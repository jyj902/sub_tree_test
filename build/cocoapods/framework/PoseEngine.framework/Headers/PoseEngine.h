#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class PoseEngineCABLE_PUSH_DOWN, PoseEngineCABLE_ROW, PoseEngineExerciseGlobal, PoseEngineDEAD_LIFT, PoseEngineEXERCISE, PoseEngineEXERCISERATING, PoseEngineEXERCISE_INFORMATION, PoseEngineEXERCISE_LIST, PoseEngineFloatsCompanion, PoseEnginePoseLandmark, PoseEngineKICK_BACK_DUMBBELL, PoseEngineLAT_PULL_DOWN, PoseEngineLEG_RAISE, PoseEngineLOGIC, PoseEngineLUNGE, PoseEngineMathCompanion, PoseEnginePointF3DCompanion, PoseEnginePointF3D, PoseEngineKotlinPair<__covariant A, __covariant B>, PoseEngineExercisePose, PoseEnginePointF, PoseEnginePoseLandmarkCompanion, PoseEnginePoseLogicCompanion, PoseEngineHHPose, PoseEngineKotlinArray<T>, PoseEnginePoseProcessCompanion, PoseEnginePoseClassifierProcessor, PoseEnginePoseProcessCablePushDownCompanion, PoseEnginePoseProcessStandingBarbellCurlCompanion, PoseEnginePoseUtilCompanion, PoseEngineSHOULDER_PRESS, PoseEngineSIDE_LATERAL_RAISE, PoseEngineSOCIAL_TYPE, PoseEngineSQUAT, PoseEngineSTANDING_BARBELL_CURL, PoseEngineURL, PoseEngineEMASmoothingCompanion, PoseEngineClassificationResult, PoseEnginePoseSample, PoseEnginePoseClassifierCompanion, PoseEnginePoseClassifierProcessorCompanion, PoseEnginePoseSampleCompanion, PoseEnginePoseEmbedding, PoseEngineRepetitionCounterCompanion, PoseEngineUtils;

@protocol PoseEngineOSAL, PoseEnginePoseInfoScreen, PoseEnginePoseProcessInterface, PoseEngineKotlinIterator;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface PoseEngineBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end;

@interface PoseEngineBase (PoseEngineBaseCopying) <NSCopying>
@end;

__attribute__((swift_name("KotlinMutableSet")))
@interface PoseEngineMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end;

__attribute__((swift_name("KotlinMutableDictionary")))
@interface PoseEngineMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end;

@interface NSError (NSErrorPoseEngineKotlinException)
@property (readonly) id _Nullable kotlinException;
@end;

__attribute__((swift_name("KotlinNumber")))
@interface PoseEngineNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end;

__attribute__((swift_name("KotlinByte")))
@interface PoseEngineByte : PoseEngineNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end;

__attribute__((swift_name("KotlinUByte")))
@interface PoseEngineUByte : PoseEngineNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end;

__attribute__((swift_name("KotlinShort")))
@interface PoseEngineShort : PoseEngineNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end;

__attribute__((swift_name("KotlinUShort")))
@interface PoseEngineUShort : PoseEngineNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end;

__attribute__((swift_name("KotlinInt")))
@interface PoseEngineInt : PoseEngineNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end;

__attribute__((swift_name("KotlinUInt")))
@interface PoseEngineUInt : PoseEngineNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end;

__attribute__((swift_name("KotlinLong")))
@interface PoseEngineLong : PoseEngineNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end;

__attribute__((swift_name("KotlinULong")))
@interface PoseEngineULong : PoseEngineNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end;

__attribute__((swift_name("KotlinFloat")))
@interface PoseEngineFloat : PoseEngineNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end;

__attribute__((swift_name("KotlinDouble")))
@interface PoseEngineDouble : PoseEngineNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end;

__attribute__((swift_name("KotlinBoolean")))
@interface PoseEngineBoolean : PoseEngineNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Greeting")))
@interface PoseEngineGreeting : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)greeting __attribute__((swift_name("greeting()")));
@end;

__attribute__((swift_name("OSAL")))
@protocol PoseEngineOSAL
@required
- (int64_t)elapsedRealtime __attribute__((swift_name("elapsedRealtime()")));
- (void)logDTag:(NSString *)tag log:(NSString *)log __attribute__((swift_name("logD(tag:log:)")));
- (void)logETag:(NSString *)tag log:(NSString *)log __attribute__((swift_name("logE(tag:log:)")));
- (void)logVTag:(NSString *)tag log:(NSString *)log __attribute__((swift_name("logV(tag:log:)")));
- (void)logWTag:(NSString *)tag log:(NSString *)log __attribute__((swift_name("logW(tag:log:)")));
- (void)playCountSoundCount:(int32_t)count param:(NSString *)param __attribute__((swift_name("playCountSound(count:param:)")));
- (void)playOnlyCountSoundCount:(int32_t)count __attribute__((swift_name("playOnlyCountSound(count:)")));
- (void)playSoundSoundName:(NSString *)soundName __attribute__((swift_name("playSound(soundName:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Platform")))
@interface PoseEnginePlatform : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (readonly) NSString *platform __attribute__((swift_name("platform")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("CABLE_PUSH_DOWN")))
@interface PoseEngineCABLE_PUSH_DOWN : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)cABLE_PUSH_DOWN __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineCABLE_PUSH_DOWN *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ELBOW_ANGLE_DOWN __attribute__((swift_name("ELBOW_ANGLE_DOWN")));
@property (readonly) NSString *ELBOW_ANGLE_UP __attribute__((swift_name("ELBOW_ANGLE_UP")));
@property (readonly) NSString *ELBOW_FIX __attribute__((swift_name("ELBOW_FIX")));
@property (readonly) NSString *HIP_FIX __attribute__((swift_name("HIP_FIX")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("CABLE_ROW")))
@interface PoseEngineCABLE_ROW : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)cABLE_ROW __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineCABLE_ROW *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("CountProcess")))
@interface PoseEngineCountProcess : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal infoScreen:(id<PoseEnginePoseInfoScreen>)infoScreen __attribute__((swift_name("init(osal:exerciseGlobal:infoScreen:)"))) __attribute__((objc_designated_initializer));
- (void)countProcessGetPoseSpeed:(NSString *)getPoseSpeed listQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)listQueue __attribute__((swift_name("countProcess(getPoseSpeed:listQueue:)")));
- (int32_t)getCounting __attribute__((swift_name("getCounting()")));
- (void)doInitReps __attribute__((swift_name("doInitReps()")));
- (void)doInitSet __attribute__((swift_name("doInitSet()")));
- (int32_t)stateProcess __attribute__((swift_name("stateProcess()")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL restClassifier __attribute__((swift_name("restClassifier")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("DEAD_LIFT")))
@interface PoseEngineDEAD_LIFT : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)dEAD_LIFT __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineDEAD_LIFT *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *BAR_WRIST __attribute__((swift_name("BAR_WRIST")));
@property (readonly) NSString *FOOT_DISTANCE __attribute__((swift_name("FOOT_DISTANCE")));
@property (readonly) NSString *HIP_ANGLE_DOWN __attribute__((swift_name("HIP_ANGLE_DOWN")));
@property (readonly) NSString *HIP_ANGLE_UP __attribute__((swift_name("HIP_ANGLE_UP")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EXERCISE")))
@interface PoseEngineEXERCISE : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)eXERCISE __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineEXERCISE *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ALREADY __attribute__((swift_name("ALREADY")));
@property (readonly) NSString *ARMS_STRAIGHT __attribute__((swift_name("ARMS_STRAIGHT")));
@property (readonly) NSString *DOWN_BAR __attribute__((swift_name("DOWN_BAR")));
@property (readonly) NSString *DOWN_WRIST __attribute__((swift_name("DOWN_WRIST")));
@property (readonly) NSString *ELBOW_STRAIGHT __attribute__((swift_name("ELBOW_STRAIGHT")));
@property (readonly) NSString *ELBOW_VERTICAL __attribute__((swift_name("ELBOW_VERTICAL")));
@property (readonly) NSString *END __attribute__((swift_name("END")));
@property (readonly) NSString *ENDING_SOUND __attribute__((swift_name("ENDING_SOUND")));
@property (readonly) NSString *END_PERFECT __attribute__((swift_name("END_PERFECT")));
@property (readonly) NSString *FAST __attribute__((swift_name("FAST")));
@property (readonly) NSString *FIX_ELBOW __attribute__((swift_name("FIX_ELBOW")));
@property (readonly) NSString *FIX_HIP __attribute__((swift_name("FIX_HIP")));
@property (readonly) NSString *FOOT_PELVIS_DISTANCE __attribute__((swift_name("FOOT_PELVIS_DISTANCE")));
@property (readonly) NSString *FOOT_STRAIGHT __attribute__((swift_name("FOOT_STRAIGHT")));
@property (readonly) NSString *FRONT __attribute__((swift_name("FRONT")));
@property (readonly) NSString *HEAD_DUMBBELL __attribute__((swift_name("HEAD_DUMBBELL")));
@property (readonly) NSString *HIP __attribute__((swift_name("HIP")));
@property (readonly) NSString *HIP_VERTICAL __attribute__((swift_name("HIP_VERTICAL")));
@property (readonly) NSString *KNEES_TOES __attribute__((swift_name("KNEES_TOES")));
@property (readonly) NSString *KNEE_DEGREE_90 __attribute__((swift_name("KNEE_DEGREE_90")));
@property (readonly) NSString *KNEE_FOOT_ANGLE __attribute__((swift_name("KNEE_FOOT_ANGLE")));
@property (readonly) NSString *LAT_PULL_DOWN_3SET_END __attribute__((swift_name("LAT_PULL_DOWN_3SET_END")));
@property (readonly) NSString *LONG_BAR __attribute__((swift_name("LONG_BAR")));
@property (readonly) NSString *LUNGE_3SET_END __attribute__((swift_name("LUNGE_3SET_END")));
@property (readonly) NSString *MOVE __attribute__((swift_name("MOVE")));
@property (readonly) NSString *RATING_1 __attribute__((swift_name("RATING_1")));
@property (readonly) NSString *RATING_2 __attribute__((swift_name("RATING_2")));
@property (readonly) NSString *RATING_3 __attribute__((swift_name("RATING_3")));
@property (readonly) NSString *RATING_4 __attribute__((swift_name("RATING_4")));
@property (readonly) NSString *RECORDING_END __attribute__((swift_name("RECORDING_END")));
@property (readonly) NSString *RECORDING_START __attribute__((swift_name("RECORDING_START")));
@property (readonly) NSString *RELAXATION __attribute__((swift_name("RELAXATION")));
@property (readonly) NSString *REPEAT __attribute__((swift_name("REPEAT")));
@property (readonly) NSString *REST __attribute__((swift_name("REST")));
@property (readonly) NSString *REST_BEFORE_10 __attribute__((swift_name("REST_BEFORE_10")));
@property (readonly) NSString *REST_BEFORE_3 __attribute__((swift_name("REST_BEFORE_3")));
@property (readonly) NSString *SET_FIRST __attribute__((swift_name("SET_FIRST")));
@property (readonly) NSString *SET_SECOND __attribute__((swift_name("SET_SECOND")));
@property (readonly) NSString *SET_THIRD __attribute__((swift_name("SET_THIRD")));
@property (readonly) NSString *SHOULDER_OVER __attribute__((swift_name("SHOULDER_OVER")));
@property (readonly) NSString *SHOULDER_PRESS_3SET_END __attribute__((swift_name("SHOULDER_PRESS_3SET_END")));
@property (readonly) NSString *SHRINK __attribute__((swift_name("SHRINK")));
@property (readonly) NSString *SIDE_HANDS __attribute__((swift_name("SIDE_HANDS")));
@property (readonly) NSString *SIDE_LATERAL_RAISE_3SET_END __attribute__((swift_name("SIDE_LATERAL_RAISE_3SET_END")));
@property (readonly) NSString *SPEED_BAD __attribute__((swift_name("SPEED_BAD")));
@property (readonly) NSString *SPEED_GOOD __attribute__((swift_name("SPEED_GOOD")));
@property (readonly) NSString *SPREAD_SHOULDER __attribute__((swift_name("SPREAD_SHOULDER")));
@property (readonly) NSString *SQUAT_3SET_END __attribute__((swift_name("SQUAT_3SET_END")));
@property (readonly) NSString *START __attribute__((swift_name("START")));
@property (readonly) NSString *UP_ELBOW __attribute__((swift_name("UP_ELBOW")));
@property (readonly) NSString *UP_TO_HEAD_DUMBBELL __attribute__((swift_name("UP_TO_HEAD_DUMBBELL")));
@property (readonly) NSString *UP_WRIST __attribute__((swift_name("UP_WRIST")));
@property (readonly) NSString *WAIST __attribute__((swift_name("WAIST")));
@property (readonly) NSString *_NO __attribute__((swift_name("_NO")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EXERCISE.RATING")))
@interface PoseEngineEXERCISERATING : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)rATING __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineEXERCISERATING *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *GOOD_1 __attribute__((swift_name("GOOD_1")));
@property (readonly) NSString *GOOD_2 __attribute__((swift_name("GOOD_2")));
@property (readonly) NSString *GOOD_3 __attribute__((swift_name("GOOD_3")));
@property (readonly) NSString *GOOD_4 __attribute__((swift_name("GOOD_4")));
@property (readonly) NSString *PERFECT_1 __attribute__((swift_name("PERFECT_1")));
@property (readonly) NSString *PERFECT_2 __attribute__((swift_name("PERFECT_2")));
@property (readonly) NSString *PERFECT_3 __attribute__((swift_name("PERFECT_3")));
@property (readonly) NSString *PERFECT_4 __attribute__((swift_name("PERFECT_4")));
@property (readonly) NSString *SOSO_1 __attribute__((swift_name("SOSO_1")));
@property (readonly) NSString *SOSO_2 __attribute__((swift_name("SOSO_2")));
@property (readonly) NSString *SOSO_3 __attribute__((swift_name("SOSO_3")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EXERCISE_INFORMATION")))
@interface PoseEngineEXERCISE_INFORMATION : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)eXERCISE_INFORMATION __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineEXERCISE_INFORMATION *shared __attribute__((swift_name("shared")));
@property (readonly) int32_t DEFAULT_REST_TIME __attribute__((swift_name("DEFAULT_REST_TIME")));
@property (readonly) int32_t FIRST_SET __attribute__((swift_name("FIRST_SET")));
@property (readonly) int32_t FULL __attribute__((swift_name("FULL")));
@property (readonly) int32_t LONG_TIME __attribute__((swift_name("LONG_TIME")));
@property (readonly) int32_t LOWER __attribute__((swift_name("LOWER")));
@property (readonly) int32_t MID_TIME __attribute__((swift_name("MID_TIME")));
@property (readonly) int32_t MODE_FREE __attribute__((swift_name("MODE_FREE")));
@property (readonly) int32_t MODE_SELECT_SET __attribute__((swift_name("MODE_SELECT_SET")));
@property (readonly) int32_t MODE_SET __attribute__((swift_name("MODE_SET")));
@property (readonly) int32_t REPS_DEFAULT __attribute__((swift_name("REPS_DEFAULT")));
@property (readonly) int32_t REPS_LONG __attribute__((swift_name("REPS_LONG")));
@property (readonly) int32_t REPS_SHOUT __attribute__((swift_name("REPS_SHOUT")));
@property (readonly) int32_t SET_DEFAULT __attribute__((swift_name("SET_DEFAULT")));
@property (readonly) int32_t SET_FIFTH_INT __attribute__((swift_name("SET_FIFTH_INT")));
@property (readonly) int32_t SET_FIRST_INT __attribute__((swift_name("SET_FIRST_INT")));
@property (readonly) int32_t SET_FORTH_INT __attribute__((swift_name("SET_FORTH_INT")));
@property (readonly) int32_t SET_LONG __attribute__((swift_name("SET_LONG")));
@property (readonly) int32_t SET_SECOND_INT __attribute__((swift_name("SET_SECOND_INT")));
@property (readonly) int32_t SET_SHOUT __attribute__((swift_name("SET_SHOUT")));
@property (readonly) int32_t SET_THIRD_INT __attribute__((swift_name("SET_THIRD_INT")));
@property (readonly) int32_t SHOUT_TIME __attribute__((swift_name("SHOUT_TIME")));
@property (readonly) int32_t STATE_EXERCISE_FINISH __attribute__((swift_name("STATE_EXERCISE_FINISH")));
@property (readonly) int32_t STATE_EXERCISE_START __attribute__((swift_name("STATE_EXERCISE_START")));
@property (readonly) int32_t STATE_EXERCISE_STOP __attribute__((swift_name("STATE_EXERCISE_STOP")));
@property (readonly) int32_t STATE_REST __attribute__((swift_name("STATE_REST")));
@property (readonly) int32_t UPPER __attribute__((swift_name("UPPER")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EXERCISE_LIST")))
@interface PoseEngineEXERCISE_LIST : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)eXERCISE_LIST __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineEXERCISE_LIST *shared __attribute__((swift_name("shared")));
- (BOOL)constrainListExerciseID:(NSString *)exerciseID __attribute__((swift_name("constrainList(exerciseID:)")));
@property (readonly) NSString *CABLE_PUSH_DOWN __attribute__((swift_name("CABLE_PUSH_DOWN")));
@property (readonly) NSString *CABLE_ROW __attribute__((swift_name("CABLE_ROW")));
@property (readonly) NSString *DEAD_LIFT __attribute__((swift_name("DEAD_LIFT")));
@property (readonly) NSString *KICK_BACK_DUMBBELL __attribute__((swift_name("KICK_BACK_DUMBBELL")));
@property (readonly) NSString *LAT_PULL_DOWN __attribute__((swift_name("LAT_PULL_DOWN")));
@property (readonly) NSString *LEG_RAISE __attribute__((swift_name("LEG_RAISE")));
@property (readonly) NSString *LUNGE __attribute__((swift_name("LUNGE")));
@property (readonly) NSString *SHOULDER_PRESS __attribute__((swift_name("SHOULDER_PRESS")));
@property (readonly) NSString *SIDE_LATERAL_RAISE __attribute__((swift_name("SIDE_LATERAL_RAISE")));
@property (readonly) NSString *SQUAT __attribute__((swift_name("SQUAT")));
@property (readonly) NSString *STANDING_BARBELL_CURL __attribute__((swift_name("STANDING_BARBELL_CURL")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExerciseGlobal")))
@interface PoseEngineExerciseGlobal : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal __attribute__((swift_name("init(osal:)"))) __attribute__((objc_designated_initializer));
@property BOOL buttonSet __attribute__((swift_name("buttonSet")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkListSound __attribute__((swift_name("checkListSound")));
@property int32_t counting __attribute__((swift_name("counting")));
@property NSMutableArray<NSString *> *dataAI __attribute__((swift_name("dataAI")));
@property NSString *exerciseID __attribute__((swift_name("exerciseID")));
@property int32_t exerciseMode __attribute__((swift_name("exerciseMode")));
@property int64_t frameTimeIn __attribute__((swift_name("frameTimeIn")));
@property int64_t frameTimeOut __attribute__((swift_name("frameTimeOut")));
@property (getter=doInitExerciseDownTime) int32_t initExerciseDownTime __attribute__((swift_name("initExerciseDownTime")));
@property (getter=doInitExerciseReps) int32_t initExerciseReps __attribute__((swift_name("initExerciseReps")));
@property (getter=doInitExerciseSet) int32_t initExerciseSet __attribute__((swift_name("initExerciseSet")));
@property (getter=doInitExerciseType) int32_t initExerciseType __attribute__((swift_name("initExerciseType")));
@property (getter=doInitExerciseUpTime) int32_t initExerciseUpTime __attribute__((swift_name("initExerciseUpTime")));
@property (getter=doInitRestTime) int32_t initRestTime __attribute__((swift_name("initRestTime")));
@property BOOL isStart __attribute__((swift_name("isStart")));
@property int32_t nowSet __attribute__((swift_name("nowSet")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL poseResult __attribute__((swift_name("poseResult")));
@property NSString *poseSpeed __attribute__((swift_name("poseSpeed")));
@property NSString *poseState __attribute__((swift_name("poseState")));
@property int32_t repsAfter __attribute__((swift_name("repsAfter")));
@property int32_t repsBefore __attribute__((swift_name("repsBefore")));
@property BOOL restBefore10Flag __attribute__((swift_name("restBefore10Flag")));
@property BOOL restBefore3Flag __attribute__((swift_name("restBefore3Flag")));
@property int64_t restTimeIn __attribute__((swift_name("restTimeIn")));
@property int64_t restTimeOut __attribute__((swift_name("restTimeOut")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ExercisePose")))
@interface PoseEngineExercisePose : PoseEngineBase
- (instancetype)initWithExerciseFlag:(int32_t)exerciseFlag nowSets:(int32_t)nowSets defaultCount:(int32_t)defaultCount allCount:(int32_t)allCount successCount:(int32_t)successCount failCount:(int32_t)failCount pose:(NSString *)pose speed:(NSString *)speed restTime:(int32_t)restTime testMessage:(NSString *)testMessage __attribute__((swift_name("init(exerciseFlag:nowSets:defaultCount:allCount:successCount:failCount:pose:speed:restTime:testMessage:)"))) __attribute__((objc_designated_initializer));
@property int32_t allCount __attribute__((swift_name("allCount")));
@property int32_t defaultCount __attribute__((swift_name("defaultCount")));
@property int32_t exerciseFlag __attribute__((swift_name("exerciseFlag")));
@property int32_t failCount __attribute__((swift_name("failCount")));
@property int32_t nowSets __attribute__((swift_name("nowSets")));
@property NSString *pose __attribute__((swift_name("pose")));
@property int32_t restTime __attribute__((swift_name("restTime")));
@property NSString *speed __attribute__((swift_name("speed")));
@property int32_t successCount __attribute__((swift_name("successCount")));
@property NSString *testMessage __attribute__((swift_name("testMessage")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Floats")))
@interface PoseEngineFloats : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) PoseEngineFloatsCompanion *companion __attribute__((swift_name("companion")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Floats.Companion")))
@interface PoseEngineFloatsCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineFloatsCompanion *shared __attribute__((swift_name("shared")));
- (float)maxA:(double)a b:(double)b c:(double)c __attribute__((swift_name("max(a:b:c:)")));
- (float)maxA:(float)a b:(float)b c_:(float)c __attribute__((swift_name("max(a:b:c_:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("HHPose")))
@interface PoseEngineHHPose : PoseEngineBase
- (instancetype)initWithLandmarks:(NSArray<PoseEnginePoseLandmark *> *)landmarks __attribute__((swift_name("init(landmarks:)"))) __attribute__((objc_designated_initializer));
- (PoseEnginePoseLandmark *)getPoseLandmarkPoseLandmarkType:(int32_t)poseLandmarkType __attribute__((swift_name("getPoseLandmark(poseLandmarkType:)")));
@property (readonly) NSArray<PoseEnginePoseLandmark *> *allPoseLandmarks __attribute__((swift_name("allPoseLandmarks")));
@property NSArray<PoseEnginePoseLandmark *> *landmarks __attribute__((swift_name("landmarks")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KICK_BACK_DUMBBELL")))
@interface PoseEngineKICK_BACK_DUMBBELL : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)kICK_BACK_DUMBBELL __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineKICK_BACK_DUMBBELL *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LAT_PULL_DOWN")))
@interface PoseEngineLAT_PULL_DOWN : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)lAT_PULL_DOWN __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineLAT_PULL_DOWN *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ELBOW_UP __attribute__((swift_name("ELBOW_UP")));
@property (readonly) NSString *ELBOW_VERTICAL __attribute__((swift_name("ELBOW_VERTICAL")));
@property (readonly) NSString *HIP_ANGLE __attribute__((swift_name("HIP_ANGLE")));
@property (readonly) NSString *WRIST_BAR __attribute__((swift_name("WRIST_BAR")));
@property (readonly) NSString *WRIST_DOWN __attribute__((swift_name("WRIST_DOWN")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LEG_RAISE")))
@interface PoseEngineLEG_RAISE : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)lEG_RAISE __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineLEG_RAISE *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *HIP_ANGLE_DOWN __attribute__((swift_name("HIP_ANGLE_DOWN")));
@property (readonly) NSString *HIP_ANGLE_UP __attribute__((swift_name("HIP_ANGLE_UP")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LOGIC")))
@interface PoseEngineLOGIC : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)lOGIC __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineLOGIC *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ACTION __attribute__((swift_name("ACTION")));
@property (readonly) NSString *LOGIC __attribute__((swift_name("LOGIC")));
@property (readonly) NSString *READY __attribute__((swift_name("READY")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LUNGE")))
@interface PoseEngineLUNGE : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)lUNGE __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineLUNGE *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *FOOT_STRAIGHT __attribute__((swift_name("FOOT_STRAIGHT")));
@property (readonly) NSString *FOOT_TO_PELVIS_DISTANCE __attribute__((swift_name("FOOT_TO_PELVIS_DISTANCE")));
@property (readonly) NSString *HIP_ANGLE __attribute__((swift_name("HIP_ANGLE")));
@property (readonly) NSString *KNEE_ANGLE_DOWN __attribute__((swift_name("KNEE_ANGLE_DOWN")));
@property (readonly) NSString *KNEE_POINT __attribute__((swift_name("KNEE_POINT")));
@property (readonly) NSString *KNEE_TO_CENTER __attribute__((swift_name("KNEE_TO_CENTER")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Math")))
@interface PoseEngineMath : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) PoseEngineMathCompanion *companion __attribute__((swift_name("companion")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Math.Companion")))
@interface PoseEngineMathCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineMathCompanion *shared __attribute__((swift_name("shared")));
- (double)absA:(double)a __attribute__((swift_name("abs(a:)")));
- (float)absA_:(float)a __attribute__((swift_name("abs(a_:)")));
- (float)hypotA:(float)a b:(float)b __attribute__((swift_name("hypot(a:b:)")));
- (float)minA:(float)a b:(float)b __attribute__((swift_name("min(a:b:)")));
- (int32_t)minA:(int32_t)a b_:(int32_t)b __attribute__((swift_name("min(a:b_:)")));
- (int32_t)roundToIntA:(float)a __attribute__((swift_name("roundToInt(a:)")));
- (double)toDegreesA:(double)a __attribute__((swift_name("toDegrees(a:)")));
@property (readonly) double PI __attribute__((swift_name("PI")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PointF")))
@interface PoseEnginePointF : PoseEngineBase
- (instancetype)initWithX:(float)x y:(float)y __attribute__((swift_name("init(x:y:)"))) __attribute__((objc_designated_initializer));
@property (readonly) float x __attribute__((swift_name("x")));
@property (readonly) float y __attribute__((swift_name("y")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PointF3D")))
@interface PoseEnginePointF3D : PoseEngineBase
- (instancetype)initWithX:(float)x y:(float)y z:(float)z __attribute__((swift_name("init(x:y:z:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePointF3DCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) float x __attribute__((swift_name("x")));
@property (readonly) float y __attribute__((swift_name("y")));
@property (readonly) float z __attribute__((swift_name("z")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PointF3D.Companion")))
@interface PoseEnginePointF3DCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePointF3DCompanion *shared __attribute__((swift_name("shared")));
- (PoseEnginePointF3D *)fromX:(float)x y:(float)y z:(float)z __attribute__((swift_name("from(x:y:z:)")));
@end;

__attribute__((swift_name("PoseInfoScreen")))
@protocol PoseEnginePoseInfoScreen
@required
- (void)finishExerciseIsFinish:(BOOL)isFinish __attribute__((swift_name("finishExercise(isFinish:)")));
- (void)postFileFile:(NSString *)file successCount:(int32_t)successCount failCount:(int32_t)failCount runningTime:(int64_t)runningTime restTime:(int64_t)restTime isFreeMode:(BOOL)isFreeMode __attribute__((swift_name("postFile(file:successCount:failCount:runningTime:restTime:isFreeMode:)")));
- (void)setCountCount:(int32_t)count __attribute__((swift_name("setCount(count:)")));
- (void)setIntfoSetData:(PoseEngineKotlinPair<NSString *, PoseEngineBoolean *> *)setData __attribute__((swift_name("setIntfo(setData:)")));
- (void)showRestTime10 __attribute__((swift_name("showRestTime10()")));
- (void)testInfoResultExercisePose:(PoseEngineExercisePose *)exercisePose __attribute__((swift_name("testInfoResult(exercisePose:)")));
- (void)userInfoResultExercisePose:(PoseEngineExercisePose *)exercisePose __attribute__((swift_name("userInfoResult(exercisePose:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseLandmark")))
@interface PoseEnginePoseLandmark : PoseEngineBase
- (instancetype)initWithLandmarkType:(int32_t)landmarkType position3D:(PoseEnginePointF3D *)position3D position:(PoseEnginePointF *)position inFrameLikelihood:(float)inFrameLikelihood __attribute__((swift_name("init(landmarkType:position3D:position:inFrameLikelihood:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseLandmarkCompanion *companion __attribute__((swift_name("companion")));
@property float inFrameLikelihood __attribute__((swift_name("inFrameLikelihood")));
@property int32_t landmarkType __attribute__((swift_name("landmarkType")));
@property PoseEnginePointF *position __attribute__((swift_name("position")));
@property PoseEnginePointF3D *position3D __attribute__((swift_name("position3D")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseLandmark.Companion")))
@interface PoseEnginePoseLandmarkCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseLandmarkCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) int32_t LEFT_ANKLE __attribute__((swift_name("LEFT_ANKLE")));
@property (readonly) int32_t LEFT_EAR __attribute__((swift_name("LEFT_EAR")));
@property (readonly) int32_t LEFT_ELBOW __attribute__((swift_name("LEFT_ELBOW")));
@property (readonly) int32_t LEFT_EYE __attribute__((swift_name("LEFT_EYE")));
@property (readonly) int32_t LEFT_EYE_INNER __attribute__((swift_name("LEFT_EYE_INNER")));
@property (readonly) int32_t LEFT_EYE_OUTER __attribute__((swift_name("LEFT_EYE_OUTER")));
@property (readonly) int32_t LEFT_FOOT_INDEX __attribute__((swift_name("LEFT_FOOT_INDEX")));
@property (readonly) int32_t LEFT_HEEL __attribute__((swift_name("LEFT_HEEL")));
@property (readonly) int32_t LEFT_HIP __attribute__((swift_name("LEFT_HIP")));
@property (readonly) int32_t LEFT_INDEX __attribute__((swift_name("LEFT_INDEX")));
@property (readonly) int32_t LEFT_KNEE __attribute__((swift_name("LEFT_KNEE")));
@property (readonly) int32_t LEFT_MOUTH __attribute__((swift_name("LEFT_MOUTH")));
@property (readonly) int32_t LEFT_PINKY __attribute__((swift_name("LEFT_PINKY")));
@property (readonly) int32_t LEFT_SHOULDER __attribute__((swift_name("LEFT_SHOULDER")));
@property (readonly) int32_t LEFT_THUMB __attribute__((swift_name("LEFT_THUMB")));
@property (readonly) int32_t LEFT_WRIST __attribute__((swift_name("LEFT_WRIST")));
@property (readonly) int32_t NOSE __attribute__((swift_name("NOSE")));
@property (readonly) int32_t RIGHT_ANKLE __attribute__((swift_name("RIGHT_ANKLE")));
@property (readonly) int32_t RIGHT_EAR __attribute__((swift_name("RIGHT_EAR")));
@property (readonly) int32_t RIGHT_ELBOW __attribute__((swift_name("RIGHT_ELBOW")));
@property (readonly) int32_t RIGHT_EYE __attribute__((swift_name("RIGHT_EYE")));
@property (readonly) int32_t RIGHT_EYE_INNER __attribute__((swift_name("RIGHT_EYE_INNER")));
@property (readonly) int32_t RIGHT_EYE_OUTER __attribute__((swift_name("RIGHT_EYE_OUTER")));
@property (readonly) int32_t RIGHT_FOOT_INDEX __attribute__((swift_name("RIGHT_FOOT_INDEX")));
@property (readonly) int32_t RIGHT_HEEL __attribute__((swift_name("RIGHT_HEEL")));
@property (readonly) int32_t RIGHT_HIP __attribute__((swift_name("RIGHT_HIP")));
@property (readonly) int32_t RIGHT_INDEX __attribute__((swift_name("RIGHT_INDEX")));
@property (readonly) int32_t RIGHT_KNEE __attribute__((swift_name("RIGHT_KNEE")));
@property (readonly) int32_t RIGHT_MOUTH __attribute__((swift_name("RIGHT_MOUTH")));
@property (readonly) int32_t RIGHT_PINKY __attribute__((swift_name("RIGHT_PINKY")));
@property (readonly) int32_t RIGHT_SHOULDER __attribute__((swift_name("RIGHT_SHOULDER")));
@property (readonly) int32_t RIGHT_THUMB __attribute__((swift_name("RIGHT_THUMB")));
@property (readonly) int32_t RIGHT_WRIST __attribute__((swift_name("RIGHT_WRIST")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseLogic")))
@interface PoseEnginePoseLogic : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal infoScreen:(id<PoseEnginePoseInfoScreen>)infoScreen __attribute__((swift_name("init(osal:exerciseGlobal:infoScreen:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseLogicCompanion *companion __attribute__((swift_name("companion")));
- (void)csvSaver __attribute__((swift_name("csvSaver()")));
- (int32_t)getExerciseStateCheckPose:(PoseEngineHHPose *)pose __attribute__((swift_name("getExerciseStateCheck(pose:)")));
- (int32_t)getStateProcess __attribute__((swift_name("getStateProcess()")));
- (void)getViewRestTimeExerciseState:(int32_t)exerciseState __attribute__((swift_name("getViewRestTime(exerciseState:)")));
- (void)poseDetectPose:(PoseEngineHHPose *)pose exercise:(NSString *)exercise __attribute__((swift_name("poseDetect(pose:exercise:)")));
- (void)saveSkeletonFloatPose:(PoseEngineHHPose *)pose csvTimeTable:(int64_t)csvTimeTable __attribute__((swift_name("saveSkeletonFloat(pose:csvTimeTable:)")));
- (void)selectExerciseTypeExercise:(NSString *)exercise __attribute__((swift_name("selectExerciseType(exercise:)")));
- (void)smootingAIPoseState:(NSString *)poseState __attribute__((swift_name("smootingAI(poseState:)")));
@property NSMutableArray<PoseEngineKotlinArray<PoseEngineFloat *> *> *csvDataListFloat __attribute__((swift_name("csvDataListFloat")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEnginePoseInfoScreen> infoScreen __attribute__((swift_name("infoScreen")));
@property NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *listQueue __attribute__((swift_name("listQueue")));
@property int64_t nowSkeletonTime __attribute__((swift_name("nowSkeletonTime")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property int64_t previousSkeletonTime __attribute__((swift_name("previousSkeletonTime")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseLogic.Companion")))
@interface PoseEnginePoseLogicCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseLogicCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess")))
@interface PoseEnginePoseProcess : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseProcessCompanion *companion __attribute__((swift_name("companion")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((swift_name("PoseProcessInterface")))
@protocol PoseEnginePoseProcessInterface
@required
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.CableLow")))
@interface PoseEnginePoseProcessCableLow : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.CablePushDown")))
@interface PoseEnginePoseProcessCablePushDown : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseProcessCablePushDownCompanion *companion __attribute__((swift_name("companion")));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.CablePushDownCompanion")))
@interface PoseEnginePoseProcessCablePushDownCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseProcessCablePushDownCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSMutableArray<PoseEngineDouble *> *hipQueue __attribute__((swift_name("hipQueue")));
@property (readonly) NSMutableArray<PoseEngineDouble *> *shouderQueue __attribute__((swift_name("shouderQueue")));
@property double sumHip __attribute__((swift_name("sumHip")));
@property double sumShoulder __attribute__((swift_name("sumShoulder")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.Companion")))
@interface PoseEnginePoseProcessCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseProcessCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.DeadLift")))
@interface PoseEnginePoseProcessDeadLift : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.LatPullDown")))
@interface PoseEnginePoseProcessLatPullDown : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.LegRaise")))
@interface PoseEnginePoseProcessLegRaise : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.Lunge")))
@interface PoseEnginePoseProcessLunge : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.ShoulderPress")))
@interface PoseEnginePoseProcessShoulderPress : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.SideLateralRaise")))
@interface PoseEnginePoseProcessSideLateralRaise : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.Squat")))
@interface PoseEnginePoseProcessSquat : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.SquatAI")))
@interface PoseEnginePoseProcessSquatAI : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.StandingBarbellCurl")))
@interface PoseEnginePoseProcessStandingBarbellCurl : PoseEngineBase <PoseEnginePoseProcessInterface>
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseProcessStandingBarbellCurlCompanion *companion __attribute__((swift_name("companion")));
- (NSString *)checkPosePose:(PoseEngineHHPose *)pose debugger:(BOOL)Debugger __attribute__((swift_name("checkPose(pose:debugger:)")));
- (PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkPosePose:(PoseEngineHHPose *)pose milsPoseState:(PoseEngineKotlinPair<NSString *, NSString *> *)milsPoseState __attribute__((swift_name("checkPose(pose:milsPoseState:)")));
- (PoseEnginePoseClassifierProcessor *)classificationType __attribute__((swift_name("classificationType()")));
- (void)soundSetCheckListQueue:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checkListQueue checkListSound:(PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *)checkListSound __attribute__((swift_name("soundSet(checkListQueue:checkListSound:)")));
@property PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *checkContracRelax __attribute__((swift_name("checkContracRelax")));
@property BOOL downFlag __attribute__((swift_name("downFlag")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property BOOL upFlag __attribute__((swift_name("upFlag")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcess.StandingBarbellCurlCompanion")))
@interface PoseEnginePoseProcessStandingBarbellCurlCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseProcessStandingBarbellCurlCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSMutableArray<PoseEngineDouble *> *hipQueue __attribute__((swift_name("hipQueue")));
@property (readonly) NSMutableArray<PoseEngineDouble *> *shouderQueue __attribute__((swift_name("shouderQueue")));
@property double sumHip __attribute__((swift_name("sumHip")));
@property double sumShoulder __attribute__((swift_name("sumShoulder")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseTime")))
@interface PoseEnginePoseTime : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal __attribute__((swift_name("init(osal:exerciseGlobal:)"))) __attribute__((objc_designated_initializer));
- (int32_t)exerciseStateCheckPose:(PoseEngineHHPose *)pose __attribute__((swift_name("exerciseStateCheck(pose:)")));
- (NSString *)exersiceTimeCheck __attribute__((swift_name("exersiceTimeCheck()")));
- (void)doInitPoseTime __attribute__((swift_name("doInitPoseTime()")));
- (PoseEngineKotlinPair<NSString *, NSString *> *)poseTime __attribute__((swift_name("poseTime()")));
- (BOOL)poseTimeFilter __attribute__((swift_name("poseTimeFilter()")));
- (BOOL)restTimeCheck __attribute__((swift_name("restTimeCheck()")));
- (void)viewRestTimeInfoScreen:(id<PoseEnginePoseInfoScreen>)infoScreen exerciseState:(int32_t)exerciseState __attribute__((swift_name("viewRestTime(infoScreen:exerciseState:)")));
@property int64_t downTime __attribute__((swift_name("downTime")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property int64_t milsFirst __attribute__((swift_name("milsFirst")));
@property NSMutableArray<NSString *> *milsPoseQueque __attribute__((swift_name("milsPoseQueque")));
@property int64_t milsSecond __attribute__((swift_name("milsSecond")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@property NSString *previousPoseState __attribute__((swift_name("previousPoseState")));
@property int64_t upTime __attribute__((swift_name("upTime")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseUtil")))
@interface PoseEnginePoseUtil : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal pose:(PoseEngineHHPose *)pose __attribute__((swift_name("init(osal:exerciseGlobal:pose:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseUtilCompanion *companion __attribute__((swift_name("companion")));
- (BOOL)checkFrameFull __attribute__((swift_name("checkFrameFull()")));
- (NSString *)checkFrameFullDebugger:(BOOL)Debugger __attribute__((swift_name("checkFrameFull(Debugger:)")));
- (BOOL)checkFrameLower __attribute__((swift_name("checkFrameLower()")));
- (BOOL)checkFrameUpper __attribute__((swift_name("checkFrameUpper()")));
- (double)getAnkleSideXFront __attribute__((swift_name("getAnkleSideXFront()")));
- (double)getCenterAngle __attribute__((swift_name("getCenterAngle()")));
- (double)getElbowAngle __attribute__((swift_name("getElbowAngle()")));
- (double)getElbowAngle3D __attribute__((swift_name("getElbowAngle3D()")));
- (double)getElbowAngleFront __attribute__((swift_name("getElbowAngleFront()")));
- (double)getElbowAngleFront3D __attribute__((swift_name("getElbowAngleFront3D()")));
- (double)getElbowToShoulder __attribute__((swift_name("getElbowToShoulder()")));
- (double)getElbowToWristBackVertical3D __attribute__((swift_name("getElbowToWristBackVertical3D()")));
- (double)getElbowVertical __attribute__((swift_name("getElbowVertical()")));
- (double)getFootAngle3D __attribute__((swift_name("getFootAngle3D()")));
- (double)getFootDistance __attribute__((swift_name("getFootDistance()")));
- (double)getFootToKnee3D __attribute__((swift_name("getFootToKnee3D()")));
- (double)getFootToKneeFront3D __attribute__((swift_name("getFootToKneeFront3D()")));
- (double)getFootToKneeFrontAverageVertical3D __attribute__((swift_name("getFootToKneeFrontAverageVertical3D()")));
- (double)getFootToKneeFrontVertical3D __attribute__((swift_name("getFootToKneeFrontVertical3D()")));
- (double)getFootToShoulderDistance __attribute__((swift_name("getFootToShoulderDistance()")));
- (double)getFoottoAnkleAngle3D __attribute__((swift_name("getFoottoAnkleAngle3D()")));
- (NSString *)getFrontKneeCheck __attribute__((swift_name("getFrontKneeCheck()")));
- (NSString *)getFrontShoulderCheck __attribute__((swift_name("getFrontShoulderCheck()")));
- (double)getHipAngle __attribute__((swift_name("getHipAngle()")));
- (double)getHipAngle3D __attribute__((swift_name("getHipAngle3D()")));
- (double)getHipAngleFront3D __attribute__((swift_name("getHipAngleFront3D()")));
- (double)getHipKnee3D __attribute__((swift_name("getHipKnee3D()")));
- (double)getKneeAngle3D __attribute__((swift_name("getKneeAngle3D()")));
- (double)getKneeAngleFront3D __attribute__((swift_name("getKneeAngleFront3D()")));
- (double)getKneeAngleLeft3D __attribute__((swift_name("getKneeAngleLeft3D()")));
- (double)getKneeAngleRight3D __attribute__((swift_name("getKneeAngleRight3D()")));
- (double)getKneeDistance __attribute__((swift_name("getKneeDistance()")));
- (double)getKneePointZ __attribute__((swift_name("getKneePointZ()")));
- (double)getKneeSideXFront __attribute__((swift_name("getKneeSideXFront()")));
- (double)getKneeToAnkle3D __attribute__((swift_name("getKneeToAnkle3D()")));
- (double)getKneetoAnklePoint __attribute__((swift_name("getKneetoAnklePoint()")));
- (double)getKneetoWristDistance __attribute__((swift_name("getKneetoWristDistance()")));
- (double)getKneetoWristPointY __attribute__((swift_name("getKneetoWristPointY()")));
- (double)getKneetoWristPointZ __attribute__((swift_name("getKneetoWristPointZ()")));
- (double)getLeftShoulderToElbowToShoulderAngle3D __attribute__((swift_name("getLeftShoulderToElbowToShoulderAngle3D()")));
- (double)getLeftWristToShuolderPointY __attribute__((swift_name("getLeftWristToShuolderPointY()")));
- (double)getPelvisDistance __attribute__((swift_name("getPelvisDistance()")));
- (double)getPelvisFrontAngle3D __attribute__((swift_name("getPelvisFrontAngle3D()")));
- (double)getRightShoulderToElbowToShoulderAngle3D __attribute__((swift_name("getRightShoulderToElbowToShoulderAngle3D()")));
- (double)getRightWristToShuolderPointY __attribute__((swift_name("getRightWristToShuolderPointY()")));
- (double)getShoulderAngle __attribute__((swift_name("getShoulderAngle()")));
- (double)getShoulderAngle3D __attribute__((swift_name("getShoulderAngle3D()")));
- (double)getShoulderAngleFront3D __attribute__((swift_name("getShoulderAngleFront3D()")));
- (double)getShoulderDistance __attribute__((swift_name("getShoulderDistance()")));
- (double)getShoulderDistance3D __attribute__((swift_name("getShoulderDistance3D()")));
- (double)getShoulderPointZ __attribute__((swift_name("getShoulderPointZ()")));
- (double)getShoulderToElbowFrontVertical3D __attribute__((swift_name("getShoulderToElbowFrontVertical3D()")));
- (double)getShoulderToElbowToElbowAngle3D __attribute__((swift_name("getShoulderToElbowToElbowAngle3D()")));
- (double)getShoulderToElbowToShoulderAngle3D __attribute__((swift_name("getShoulderToElbowToShoulderAngle3D()")));
- (double)getShoulderToHipFrontVertical3D __attribute__((swift_name("getShoulderToHipFrontVertical3D()")));
- (double)getShoulderToWristAngle3D __attribute__((swift_name("getShoulderToWristAngle3D()")));
- (double)getShoulderToWristToShoulderAngle3D __attribute__((swift_name("getShoulderToWristToShoulderAngle3D()")));
- (double)getShoulderVertical __attribute__((swift_name("getShoulderVertical()")));
- (double)getWrisTtoElbowPointY __attribute__((swift_name("getWrisTtoElbowPointY()")));
- (double)getWrist3D __attribute__((swift_name("getWrist3D()")));
- (double)getWristDistance __attribute__((swift_name("getWristDistance()")));
- (double)getWristPointZ __attribute__((swift_name("getWristPointZ()")));
- (double)getWristToEar __attribute__((swift_name("getWristToEar()")));
- (double)getWristToShoulder __attribute__((swift_name("getWristToShoulder()")));
- (double)getWristToShuolderPointY __attribute__((swift_name("getWristToShuolderPointY()")));
- (double)getWristtoShoulderPointZ __attribute__((swift_name("getWristtoShoulderPointZ()")));
- (BOOL)startHand __attribute__((swift_name("startHand()")));
- (BOOL)stopHand __attribute__((swift_name("stopHand()")));
- (double)testLeftWristZ __attribute__((swift_name("testLeftWristZ()")));
- (double)testRightWristZ __attribute__((swift_name("testRightWristZ()")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseUtil.Companion")))
@interface PoseEnginePoseUtilCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseUtilCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SHOULDER_PRESS")))
@interface PoseEngineSHOULDER_PRESS : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)sHOULDER_PRESS __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineSHOULDER_PRESS *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *DOWN_WRIST __attribute__((swift_name("DOWN_WRIST")));
@property (readonly) NSString *ELBOW_ANGLE __attribute__((swift_name("ELBOW_ANGLE")));
@property (readonly) NSString *ELBOW_DOWN __attribute__((swift_name("ELBOW_DOWN")));
@property (readonly) NSString *ELBOW_FRONT __attribute__((swift_name("ELBOW_FRONT")));
@property (readonly) NSString *ELBOW_UP __attribute__((swift_name("ELBOW_UP")));
@property (readonly) NSString *WRIST_3D __attribute__((swift_name("WRIST_3D")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SIDE_LATERAL_RAISE")))
@interface PoseEngineSIDE_LATERAL_RAISE : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)sIDE_LATERAL_RAISE __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineSIDE_LATERAL_RAISE *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ELBOW_ANGLE __attribute__((swift_name("ELBOW_ANGLE")));
@property (readonly) NSString *ELBOW_ANGLE_DOWN __attribute__((swift_name("ELBOW_ANGLE_DOWN")));
@property (readonly) NSString *ELBOW_ANGLE_UP __attribute__((swift_name("ELBOW_ANGLE_UP")));
@property (readonly) NSString *HIP_ANGLE __attribute__((swift_name("HIP_ANGLE")));
@property (readonly) NSString *SHOULDER_TO_WRIST __attribute__((swift_name("SHOULDER_TO_WRIST")));
@property (readonly) NSString *WRIST_3D __attribute__((swift_name("WRIST_3D")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SOCIAL_TYPE")))
@interface PoseEngineSOCIAL_TYPE : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)sOCIAL_TYPE __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineSOCIAL_TYPE *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *GOOGLE __attribute__((swift_name("GOOGLE")));
@property (readonly) NSString *KAKAO __attribute__((swift_name("KAKAO")));
@property (readonly) NSString *NAVER __attribute__((swift_name("NAVER")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SQUAT")))
@interface PoseEngineSQUAT : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)sQUAT __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineSQUAT *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *FOOT_TO_SHOULDER_DISTANCE __attribute__((swift_name("FOOT_TO_SHOULDER_DISTANCE")));
@property (readonly) NSString *HIP_ANGLE __attribute__((swift_name("HIP_ANGLE")));
@property (readonly) NSString *KNEE_ANGLE_DOWN __attribute__((swift_name("KNEE_ANGLE_DOWN")));
@property (readonly) NSString *KNEE_DISTANCE __attribute__((swift_name("KNEE_DISTANCE")));
@property (readonly) NSString *KNEE_POINT __attribute__((swift_name("KNEE_POINT")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("STANDING_BARBELL_CURL")))
@interface PoseEngineSTANDING_BARBELL_CURL : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)sTANDING_BARBELL_CURL __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineSTANDING_BARBELL_CURL *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *ELBOW_ANGLE_DOWN __attribute__((swift_name("ELBOW_ANGLE_DOWN")));
@property (readonly) NSString *ELBOW_ANGLE_UP __attribute__((swift_name("ELBOW_ANGLE_UP")));
@property (readonly) NSString *ELBOW_FIX __attribute__((swift_name("ELBOW_FIX")));
@property (readonly) NSString *HIP_FIX __attribute__((swift_name("HIP_FIX")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("URL")))
@interface PoseEngineURL : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)uRL __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineURL *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *SKELETON __attribute__((swift_name("SKELETON")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ClassificationResult")))
@interface PoseEngineClassificationResult : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSSet<NSString *> *)getAllClasses __attribute__((swift_name("getAllClasses()")));
- (float)getClassConfidenceClassName:(NSString *)className __attribute__((swift_name("getClassConfidence(className:)")));
- (NSString *)getMaxConfidenceClass __attribute__((swift_name("getMaxConfidenceClass()")));
- (void)incrementClassConfidenceClassName:(NSString * _Nullable)className __attribute__((swift_name("incrementClassConfidence(className:)")));
- (void)putClassConfidenceClassName:(NSString *)className confidence:(float)confidence __attribute__((swift_name("putClassConfidence(className:confidence:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EMASmoothing")))
@interface PoseEngineEMASmoothing : PoseEngineBase
- (instancetype)initWithWindowSize:(int32_t)windowSize alpha:(float)alpha __attribute__((swift_name("init(windowSize:alpha:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEngineEMASmoothingCompanion *companion __attribute__((swift_name("companion")));
- (PoseEngineClassificationResult *)getSmoothedResultClassificationResult:(PoseEngineClassificationResult *)classificationResult __attribute__((swift_name("getSmoothedResult(classificationResult:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("EMASmoothing.Companion")))
@interface PoseEngineEMASmoothingCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineEMASmoothingCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseClassifier")))
@interface PoseEnginePoseClassifier : PoseEngineBase
- (instancetype)initWithPoseSamples:(NSArray<PoseEnginePoseSample *> *)poseSamples Type:(int32_t)Type __attribute__((swift_name("init(poseSamples:Type:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithPoseSamples:(NSArray<PoseEnginePoseSample *> *)poseSamples Type:(int32_t)Type maxDistanceTopK:(int32_t)maxDistanceTopK meanDistanceTopK:(int32_t)meanDistanceTopK axesWeights:(PoseEnginePointF3D *)axesWeights __attribute__((swift_name("init(poseSamples:Type:maxDistanceTopK:meanDistanceTopK:axesWeights:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseClassifierCompanion *companion __attribute__((swift_name("companion")));
- (PoseEngineClassificationResult *)classifyPose:(PoseEngineHHPose *)pose __attribute__((swift_name("classify(pose:)")));
- (PoseEngineClassificationResult *)classifyLandmarks:(NSMutableArray<PoseEnginePointF3D *> *)landmarks __attribute__((swift_name("classify(landmarks:)")));
- (int32_t)confidenceRange __attribute__((swift_name("confidenceRange()")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseClassifier.Companion")))
@interface PoseEnginePoseClassifierCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseClassifierCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseClassifierProcessor")))
@interface PoseEnginePoseClassifierProcessor : PoseEngineBase
- (instancetype)initWithOsal:(id<PoseEngineOSAL>)osal exerciseGlobal:(PoseEngineExerciseGlobal *)exerciseGlobal isStreamMode:(BOOL)isStreamMode Exercise:(PoseEngineKotlinPair<NSString *, PoseEngineInt *> *)Exercise __attribute__((swift_name("init(osal:exerciseGlobal:isStreamMode:Exercise:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseClassifierProcessorCompanion *companion __attribute__((swift_name("companion")));
- (NSArray<id> *)getPoseResultPose:(PoseEngineHHPose *)pose __attribute__((swift_name("getPoseResult(pose:)")));
@property (readonly) PoseEngineExerciseGlobal *exerciseGlobal __attribute__((swift_name("exerciseGlobal")));
@property (readonly) id<PoseEngineOSAL> osal __attribute__((swift_name("osal")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseClassifierProcessor.Companion")))
@interface PoseEnginePoseClassifierProcessorCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseClassifierProcessorCompanion *shared __attribute__((swift_name("shared")));
@property PoseEngineKotlinPair<NSString *, PoseEngineInt *> * _Nullable CompanionExercise __attribute__((swift_name("CompanionExercise")));
@property PoseEngineInt * _Nullable type __attribute__((swift_name("type")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseEmbedding")))
@interface PoseEnginePoseEmbedding : PoseEngineBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSArray<PoseEnginePointF3D *> *)getPoseEmbeddingLandmarks:(NSArray<PoseEnginePointF3D *> *)landmarks Type:(int32_t)Type __attribute__((swift_name("getPoseEmbedding(landmarks:Type:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseSample")))
@interface PoseEnginePoseSample : PoseEngineBase
- (instancetype)initWithName:(NSString *)name className:(NSString *)className landmarks:(NSArray<PoseEnginePointF3D *> *)landmarks __attribute__((swift_name("init(name:className:landmarks:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEnginePoseSampleCompanion *companion __attribute__((swift_name("companion")));
- (NSString *)_getClassName __attribute__((swift_name("_getClassName()")));
- (NSArray<PoseEnginePointF3D *> *)getEmbedding __attribute__((swift_name("getEmbedding()")));
@property (readonly) NSString *className __attribute__((swift_name("className")));
@property PoseEnginePoseEmbedding *embedding __attribute__((swift_name("embedding")));
@property NSArray<PoseEnginePointF3D *> *landark __attribute__((swift_name("landark")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseSample.Companion")))
@interface PoseEnginePoseSampleCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEnginePoseSampleCompanion *shared __attribute__((swift_name("shared")));
- (PoseEnginePoseSample * _Nullable)getPoseSampleCsvLine:(NSString * _Nullable)csvLine separator:(NSString * _Nullable)separator __attribute__((swift_name("getPoseSample(csvLine:separator:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RepetitionCounter")))
@interface PoseEngineRepetitionCounter : PoseEngineBase
- (instancetype)initWithClassName:(NSString *)className enterThreshold:(float)enterThreshold exitThreshold:(float)exitThreshold __attribute__((swift_name("init(className:enterThreshold:exitThreshold:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) PoseEngineRepetitionCounterCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)_getNumRepeats __attribute__((swift_name("_getNumRepeats()")));
- (int32_t)addClassificationResultClassificationResult:(PoseEngineClassificationResult *)classificationResult __attribute__((swift_name("addClassificationResult(classificationResult:)")));
@property (readonly) NSString *className __attribute__((swift_name("className")));
@property (readonly) int32_t numRepeats __attribute__((swift_name("numRepeats")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RepetitionCounter.Companion")))
@interface PoseEngineRepetitionCounterCompanion : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineRepetitionCounterCompanion *shared __attribute__((swift_name("shared")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("Utils")))
@interface PoseEngineUtils : PoseEngineBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)utils __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) PoseEngineUtils *shared __attribute__((swift_name("shared")));
- (PoseEnginePointF3D *)addA:(PoseEnginePointF3D *)a b:(PoseEnginePointF3D *)b __attribute__((swift_name("add(a:b:)")));
- (void)addAllPointsList:(NSMutableArray<PoseEnginePointF3D *> *)pointsList p:(PoseEnginePointF3D *)p __attribute__((swift_name("addAll(pointsList:p:)")));
- (PoseEnginePointF3D *)averageA:(PoseEnginePointF3D *)a b:(PoseEnginePointF3D *)b __attribute__((swift_name("average(a:b:)")));
- (float)l2Norm2DPoint:(PoseEnginePointF3D *)point __attribute__((swift_name("l2Norm2D(point:)")));
- (float)maxAbsPoint:(PoseEnginePointF3D *)point __attribute__((swift_name("maxAbs(point:)")));
- (PoseEnginePointF3D *)multiplyA:(PoseEnginePointF3D *)a multiple:(PoseEnginePointF3D *)multiple __attribute__((swift_name("multiply(a:multiple:)")));
- (PoseEnginePointF3D *)multiplyA:(PoseEnginePointF3D *)a multiple_:(float)multiple __attribute__((swift_name("multiply(a:multiple_:)")));
- (void)multiplyAllPointsList:(NSMutableArray<PoseEnginePointF3D *> *)pointsList multiple:(PoseEnginePointF3D *)multiple __attribute__((swift_name("multiplyAll(pointsList:multiple:)")));
- (void)multiplyAllPointsList:(NSMutableArray<PoseEnginePointF3D *> *)pointsList multiple_:(float)multiple __attribute__((swift_name("multiplyAll(pointsList:multiple_:)")));
- (PoseEnginePointF3D *)subtractB:(PoseEnginePointF3D *)b a:(PoseEnginePointF3D *)a __attribute__((swift_name("subtract(b:a:)")));
- (void)subtractAllP:(PoseEnginePointF3D *)p pointsList:(NSMutableArray<PoseEnginePointF3D *> *)pointsList __attribute__((swift_name("subtractAll(p:pointsList:)")));
- (float)sumAbsPoint:(PoseEnginePointF3D *)point __attribute__((swift_name("sumAbs(point:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConstantKt")))
@interface PoseEngineConstantKt : PoseEngineBase
@property (class, readonly) PoseEngineKotlinArray<NSString *> *DIFFICULTY __attribute__((swift_name("DIFFICULTY")));
@property (class, readonly) NSString *KEY __attribute__((swift_name("KEY")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseUtilKt")))
@interface PoseEnginePoseUtilKt : PoseEngineBase
+ (double)averageX:(double)x x1:(double)x1 __attribute__((swift_name("average(x:x1:)")));
+ (float)averageX:(float)x x1_:(float)x1 __attribute__((swift_name("average(x:x1_:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("PoseProcessKt")))
@interface PoseEnginePoseProcessKt : PoseEngineBase
+ (BOOL)wrongPoseCheckCheckstate:(NSString *)checkstate checklist:(NSMutableArray<PoseEngineMutableDictionary<NSString *, PoseEngineBoolean *> *> *)checklist __attribute__((swift_name("wrongPoseCheck(checkstate:checklist:)")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinPair")))
@interface PoseEngineKotlinPair<__covariant A, __covariant B> : PoseEngineBase
- (instancetype)initWithFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("init(first:second:)"))) __attribute__((objc_designated_initializer));
- (A _Nullable)component1 __attribute__((swift_name("component1()")));
- (B _Nullable)component2 __attribute__((swift_name("component2()")));
- (PoseEngineKotlinPair<A, B> *)doCopyFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("doCopy(first:second:)")));
- (BOOL)equalsOther:(id _Nullable)other __attribute__((swift_name("equals(other:)")));
- (int32_t)hashCode __attribute__((swift_name("hashCode()")));
- (NSString *)toString __attribute__((swift_name("toString()")));
@property (readonly) A _Nullable first __attribute__((swift_name("first")));
@property (readonly) B _Nullable second __attribute__((swift_name("second")));
@end;

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface PoseEngineKotlinArray<T> : PoseEngineBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(PoseEngineInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<PoseEngineKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end;

__attribute__((swift_name("KotlinIterator")))
@protocol PoseEngineKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end;

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
