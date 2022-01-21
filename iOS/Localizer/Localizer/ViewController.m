//
//  ViewController.m
//  Localizer
//
//  Created by 김기훈 on 2021/11/24.
//

#import "ViewController.h"
#import "CHCSVParser.h"

@interface ViewController ()
@property (weak) IBOutlet NSTextField *tfInputFilePath;
@property (weak) IBOutlet NSTextField *tfOutputFolderPath;
@property (weak) IBOutlet NSTextField *tfStringConstFilePath;
@property (weak) IBOutlet NSTextField *tfResult;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    // Do any additional setup after loading the view.
    NSString* strInputFilePath = [NSUserDefaults.standardUserDefaults objectForKey: @"InputFilePath"];
    NSString* strOutputFolderPath = [NSUserDefaults.standardUserDefaults objectForKey: @"OutputFolderPath"];
    NSString* strStringConstFilePath = [NSUserDefaults.standardUserDefaults objectForKey: @"StringConstFilePath"];

    if (strInputFilePath != nil) {
        self.tfInputFilePath.stringValue = strInputFilePath;
    }
    if (strOutputFolderPath != nil) {
        self.tfOutputFolderPath.stringValue = strOutputFolderPath;
    }
    if (strStringConstFilePath != nil) {
        self.tfStringConstFilePath.stringValue = strStringConstFilePath;
    }
}
- (void)viewDidDisappear {
    [super viewDidDisappear];
    NSLog(@"viewDidDisappear");
    
    [NSUserDefaults.standardUserDefaults setObject:self.tfInputFilePath.stringValue forKey:@"InputFilePath"];
    [NSUserDefaults.standardUserDefaults setObject:self.tfOutputFolderPath.stringValue forKey:@"OutputFolderPath"];
    [NSUserDefaults.standardUserDefaults setObject:self.tfStringConstFilePath.stringValue forKey:@"StringConstFilePath"];
}

- (void)setRepresentedObject:(id)representedObject {
    [super setRepresentedObject:representedObject];

    // Update the view, if already loaded.
}

- (IBAction)onClickGenerate:(id)sender {
    self.tfResult.stringValue = @"처리중입니다.";
    
    // csv 파일 읽어들이기
    NSError *error = nil;
    NSString* inputFilePath = self.tfInputFilePath.stringValue;
    NSString* outputFolderPath = self.tfOutputFolderPath.stringValue;
    NSLog(@"inputFilePath = %@", inputFilePath);
    NSLog(@"outputFolderPath = %@", outputFolderPath);

    NSString *dataString = [NSString stringWithContentsOfFile:inputFilePath encoding:NSUTF8StringEncoding error:&error];
    if (error != nil) {
        NSLog(@"inputFilePath = %@", inputFilePath);
        NSLog(@"error = %@", error.localizedDescription);
    }

    NSArray *dataMatrix;
    NSMutableArray *langCodeList;
    if(dataString.length > 0) {
        dataMatrix = [dataString CSVComponentsWithOptions:0];
        langCodeList = [dataMatrix[0] mutableCopy]; // 첫번째 줄은 국가 코드가 들어 있는 헤더 컬럼
        
        NSLog(@"data = %@", dataMatrix);
    }

    for(int x = 1; x < langCodeList.count; x++) {
        NSMutableDictionary *dict = [[NSMutableDictionary alloc]init];
        NSString* folderName = [outputFolderPath componentsSeparatedByString:@"/"].lastObject;
        NSString* langCode = langCodeList[x];
    
        NSString *filePath = [NSString stringWithFormat:@"%@/%@.lproj/%@.strings", outputFolderPath, langCode, folderName];

        for(int y = 1; y < dataMatrix.count; y++) {
            NSArray *row = dataMatrix[y];
            NSString *tokenName = row[0];
            NSString *value = row[x]; // 해당 국가의 value
            dict[tokenName] = value;
        }
        [self writeFile:dict toURL:[NSURL fileURLWithPath:filePath]];
        if ([langCode isEqualToString:@"ko"]) {
            [self writeConstStringFile:dict toURL:[NSURL fileURLWithPath:self.tfStringConstFilePath.stringValue]];
        }
        NSLog(@"string file data = %@", dict);
    }
    self.tfResult.stringValue = @"모두 처리되었습니다.";
}

- (BOOL)writeFile:(NSDictionary *)dictionary toURL:(NSURL *)url {
    NSArray* sortedKeys = [dictionary.allKeys sortedArrayUsingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
        NSString *v1 = obj1;
        NSString *v2 = obj2;

        return [v1 compare:v2 options:NSCaseInsensitiveSearch];
    }];

    NSMutableString *fileContent = [NSMutableString new];
    [fileContent appendFormat:@"// by kihoon.kim\n"];
    [fileContent appendFormat:@"// 툴에 의해서 자동으로 generate 되는 파일입니다.\n"];

    for(NSString *key in sortedKeys) {
        NSString *value = dictionary[key];
        [fileContent appendFormat:@"\"%@\" = \"%@\";\n", [self escapeString:key], [self escapeString:value]];
    }
    
    [fileContent appendFormat:@"// -------------------------------------\n"];
    [fileContent appendFormat:@"// 여기서부터 추가되는 string을 기입하세요.\n"];

    NSError *error = nil;
    BOOL result = [fileContent writeToURL:url atomically:YES encoding:NSUTF8StringEncoding error:&error];
    if (error != nil) {
        NSLog(@"url = %@", url);
        NSLog(@"error = %@", error.localizedDescription);
    }

    return result;
}

- (BOOL)writeConstStringFile:(NSDictionary *)dictionary toURL:(NSURL *)url {
    NSArray* sortedKeys = [dictionary.allKeys sortedArrayUsingComparator:^NSComparisonResult(id  _Nonnull obj1, id  _Nonnull obj2) {
        NSString *v1 = obj1;
        NSString *v2 = obj2;

        return [v1 compare:v2 options:NSCaseInsensitiveSearch];
    }];

    NSMutableString *fileContent = [NSMutableString new];

    [fileContent appendFormat:@"// by kihoon.kim\n"];
    [fileContent appendFormat:@"// 툴에 의해서 자동으로 generate 되는 파일입니다.\n"];
    [fileContent appendFormat:@"public enum StringConst: String {\n"];

    for(NSString *key in sortedKeys) {
        NSString *value = dictionary[key];
        [fileContent appendFormat:@"\tcase %@\t\t//\"%@\"\n", [self escapeString:key], [self escapeString:value]];
    }
    [fileContent appendFormat:@"\t// -------------------------------------\n"];
    [fileContent appendFormat:@"\t// 여기서부터 추가되는 string을 기입하세요.\n"];

    [fileContent appendFormat:@"}\n"];

    NSError *error = nil;
    BOOL result = [fileContent writeToURL:url atomically:YES encoding:NSUTF8StringEncoding error:&error];
    if (error != nil) {
        NSLog(@"url = %@", url);
        NSLog(@"error = %@", error.localizedDescription);
    }

    return result;
}


/// CSV format을 복원한다.
/// @param string CSV 필드 문자
/* CSV 포맷 규칙
 1.  Each record is located on a separate line, delimited by a line break (CRLF).
 2.  The last record in the file may or may not have an ending line break.
 3.  There maybe an optional header line appearing as the first line of the file with the same format as normal record lines.  This header will contain names corresponding to the fields in the file and should contain the same number of fields as the records in the rest of the file (the presence or absence of the header line should be indicated via the optional "header" parameter of this MIME type).
 4.  Within the header and each record, there may be one or more fields, separated by commas.  Each line should contain the same number of fields throughout the file.  Spaces are considered part of a field and should not be ignored.  The last field in the record must not be followed by a comma.
 5.  Each field may or may not be enclosed in double quotes (however some programs, such as Microsoft Excel, do not use double quotes at all).  If fields are not enclosed with double quotes, then double quotes may not appear inside the fields.
 6.  Fields containing line breaks (CRLF), double quotes, and commas should be enclosed in double-quotes.
 7.  If double-quotes are used to enclose fields, then a double-quote appearing inside a field must be escaped by preceding it with another double quote.
 */
- (NSString *)escapeString:(NSString *)string {
    NSString *escapedString = string;
    
    /* CSV 규칙 6에 의해 앞뒤로 쌍따옴표가 있을 경우...
     CSV 파일은 문장에 줄바꿈이 있거나 쉼표, 쌍따옴표 등이 있을 경우 문장 앞뒤에 쌍따옴표가 붙는다. 이를 제거한다.
     */
    escapedString = [string stringByTrimmingCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"\""]];

    /* 규칙 7번
      쌍따옴표가 연속해서 2개일 경우 쌍따옴표 문자를 나타낸다.(쌍따옴표로 escaping한다)
     */
    if([escapedString containsString:@"\"\""]) {
        escapedString = [escapedString stringByReplacingOccurrencesOfString:@"\"\"" withString:@"\""];
    }

    // 줄바꿈 문자가 있을 경우 모두 "\n"으로 변경한다.
    NSArray *comps = [escapedString componentsSeparatedByCharactersInSet:NSCharacterSet.newlineCharacterSet];
    if(comps.count > 1) {
        NSString *temp = @"";
        for(int i = 0; i < comps.count; i++) {
            NSString *line = comps[i];
            temp = [temp stringByAppendingString:line];
            if((i + 1) < comps.count) {
                temp = [temp stringByAppendingString:@"\\n"];
            }
        }
        
        escapedString = temp;
    }
    
    // "," 쉼표를 처리한다.
    if([escapedString containsString:@"\",\""]) {
        escapedString = [escapedString stringByReplacingOccurrencesOfString:@"\",\"" withString:@","];
    }
    
    // 일반적인 쌍따옴표를 처리한다.
    comps = [escapedString componentsSeparatedByString:@"\""];
    if(comps.count > 1) {
        NSString *temp = @"";
        for(int i = 0; i < comps.count; i++) {
            NSString *line = comps[i];
            temp = [temp stringByAppendingString:line];
            if((i + 1) < comps.count) {
                temp = [temp stringByAppendingString:@"\""];
            }
        }
        escapedString = temp;
    }

    if([escapedString containsString:@"\""]) {
        escapedString = [escapedString stringByReplacingOccurrencesOfString:@"\"" withString:@"\\\""];
    }

    return escapedString;
}
@end
