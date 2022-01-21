//
//  Title.swift
//  SampleCollection
//
//  Created by kihoon.kim on 2018. 4. 10..
//  Copyright © 2018년 com.hnh. All rights reserved.
//

import SystemConfiguration
import Moya
import RxSwift
import Alamofire

// MARK: - Public Outter Class, Struct, Enum, Protocol
protocol NetworkingProtocol {
    func request(_ target: TargetType, file: StaticString, function: StaticString, line: UInt) -> Single<Response>
}

extension NetworkingProtocol {
    func request(_ target: TargetType, file: StaticString = #file, function: StaticString = #function, line: UInt = #line) -> Single<Response> {
        return self.request(target, file: file, function: function, line: line)
    }
}

class NetworkingStyle {
    static let verboseLogger       = 1 << 0
    static let activityIndicator   = 1 << 1
    static let sampleData          = 1 << 2
    static let appExitOnError      = 1 << 3
}

class Networking: MoyaProvider<MultiTarget>, NetworkingProtocol {
    // MARK: - Public Inner Class, Struct, Enum, Protocol
    // MARK: - Public Variable

    // MARK: - Private Variable
    private static let timeoutInterval:Double = 30
    private var style:HHStyle?
    private var requestNetworkSubject:PublishSubject<Void>?

    // MARK: - Override Method or Basic Method
    init(style: HHStyle, requestNetworkSubject:PublishSubject<Void>?) {
        var plugins: [PluginType] = []
        var endpointClosure:EndpointClosure = MoyaProvider.defaultEndpointMapping
        var stubClosure:StubClosure = MoyaProvider.neverStub
        
        if style.isInclude(NetworkingStyle.verboseLogger) {
            let networkLogger = NetworkLoggerPlugin(configuration: .init(logOptions: .default))
            plugins.append(networkLogger)
        }
        
        if style.isInclude(NetworkingStyle.activityIndicator) {
            let networkActivityClosure = NetworkActivityPlugin(networkActivityClosure: NetworkingAPIService.networkClosure)
            plugins.append(networkActivityClosure)
        }

        if style.isInclude(NetworkingStyle.sampleData) {
            endpointClosure = { (target : MultiTarget) -> Endpoint in
                return Endpoint(url: URL(target: target).absoluteString, sampleResponseClosure: {.networkResponse(200, target.sampleData)}, method: target.method, task: target.task, httpHeaderFields: target.headers)
            }
            stubClosure = MoyaProvider.immediatelyStub
        }
        
        self.requestNetworkSubject = requestNetworkSubject

        let configuration = URLSessionConfiguration.default
        configuration.timeoutIntervalForRequest = Networking.timeoutInterval
        configuration.timeoutIntervalForResource = Networking.timeoutInterval
        let session = Session(configuration: configuration)
        HHLog.d(.network, "timeoutIntervalForRequest = \(session.session.configuration.timeoutIntervalForRequest)")

        super.init(endpointClosure: endpointClosure, stubClosure: stubClosure, session:session , plugins: plugins)
    }
    
    // MARK: - Public Method
    func request(_ target: TargetType, file: StaticString = #file, function: StaticString = #function, line: UInt = #line) -> Single<Response> {
        return self.rx.request(.target(target))
            .do(onSuccess: { value in
                    HHLog.d(.network, "response: \(String(decoding: value.data, as: UTF8.self))")
                },
                onError: { [weak self] error in
                    self?.errorRecovery(error)
                })
    }
        
    // MARK: - Private Method
    private func errorRecovery(_ error:Error) {
        HHLog.e(.network, .no0041, "errorRecovery() error = \(error)")
        
        delayExecute(0.5) { [weak self] in
            HHLog.t(.network)
            let okText:String?
            let okHandler:HHDialogViewController.OkHandler?
            let cancelText:String?
            let cancelHandler:HHDialogViewController.CancelHandler?
            
            if self?.style?.isInclude(NetworkingStyle.appExitOnError) ?? false {
                okText = TODO("앱종료")
                okHandler = {
                    (UIApplication.shared.delegate as? AppDelegate)?.finishApp()
                }
            } else {
                okText = TODO("확인")
                okHandler = {
                    // do nothing
                }
            }
            
            if self?.requestNetworkSubject == nil {
                cancelText = nil
                cancelHandler = nil
            } else {
                cancelText = TODO("재시도")
                cancelHandler = { [weak self] in
                    //retry
                    self?.requestNetworkSubject?.onNext(Void())
                }
            }

            HHDialog(style:HHStyle(HHDialogStyle.none), titleText: TODO("서버에러"), contentsText: TODO("네트워크 연결이 원활하지 않습니다. 나중에 다시 시도해 보세요."), okText:okText, okHandler:okHandler , cancelText:cancelText , cancelHandler:cancelHandler )
        }
    }
}

/// API 관련 공통.
public class NetworkingAPIService {
    /// Moya NetworkActivityPlugin 클로저.
    static let networkClosure = {(_ change: NetworkActivityChangeType, _ target: TargetType) in
        switch change {
        case .began:
            DispatchQueue.main.async {
                LoadingView.shared.show()
            }
        case .ended:
            DispatchQueue.main.async {
                LoadingView.shared.dismiss()
            }
        }
    }
}
