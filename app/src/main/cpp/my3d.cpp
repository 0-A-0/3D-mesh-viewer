// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("my3d");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("my3d")
//      }
//    }
#include <jni.h>
#include <vector>
#include <cmath>

using namespace std;
static jfieldID fid_x = nullptr;
static jfieldID fid_y = nullptr;
static jfieldID fid_z = nullptr;
static float propX = 10000.0f;
static float propY = 5000.0f;

//static float zc = 30.0f;
//static float z0 = 50.0f;
//static float x0 = 0.0f;
//static float yO = 0.0f;
struct Matrix_4_4{
    float m[4][4];
};

struct Array_4{
    float arr[4];
};

void cacheFieldsId(JNIEnv *env){
    jclass Point = env->FindClass("com/example/my3d/ui/theme/structure/Point");
    fid_x = env->GetFieldID(Point,"x","F");
    fid_y = env->GetFieldID(Point,"y","F");
    fid_z = env->GetFieldID(Point,"z","F");
    env->DeleteLocalRef(Point);
}

Matrix_4_4 rotateOx(float delta) {
    float c = cos(delta);
    float s = sin(delta);
    return {
            {{1.0f, 0.0f, 0.0f, 0.0f},
             {0.0f, c, s, 0.0f},
             {0.0f, -s, c, 0.0f},
             {0.0f, 0.0f, 0.0f, 1.0f}}
    };
}
Matrix_4_4 rotateOy(float delta){
    float c = cos(delta);
    float s = sin(delta);
    return {
            {{c, 0.0f, -s, 0.0f},
             {0.0f, 1.0f, 0.0f, 0.0f},
             {s, 0.0f, c, 0.0f},
             {0.0f, 0.0f, 0.0f, 1.0f}}
    };
}
Matrix_4_4 multMatrix(const Matrix_4_4& a, const Matrix_4_4& b) {
    Matrix_4_4 result = {};
    for (int i = 0; i < 4; i++)
        for (int j = 0; j < 4; j++) {
            result.m[i][j] = 0;
            for (int k = 0; k < 4; k++)
                result.m[i][j] += a.m[i][k] * b.m[k][j];
        }
    return result;
}

Array_4 mult(const Matrix_4_4& t,const Array_4& m){
    Array_4 result = {0.0f,0.0f,0.0f,0.0f};
    for (int i = 0;i <4;i++) {
        float s = 0.0f;
        for (int j = 0;j <4;j++) {
            s += t.m[j][i] * m.arr[j];
        }
        result.arr[i] = s;
    }
    return result;
}
//float xs(float x,float y,float z){
//    return (x - x0) / (z - z0) * (zc - z0) + x0;
//}
//float ys(float x,float y,float z) {
//    return (y - yO) / (z - z0) * (zc - z0) + yO;
//}
jint JNI_OnLoad(JavaVM* vm,void* reserved){
    JNIEnv* env;
    vm->GetEnv(reinterpret_cast<void**>(&env),JNI_VERSION_1_6);
    cacheFieldsId(env);
    return JNI_VERSION_1_6;
}
extern "C" {
//JNIEXPORT void JNICALL
//Java_com_example_my3d_ui_theme_canvas_CppFuns_00024Companion_mult(JNIEnv *env, jobject thiz, jfloatArray t,
//                                                   jfloatArray m, jfloatArray result) {
//    jfloat *T = env->GetFloatArrayElements(t, nullptr);
//    jfloat *M = env->GetFloatArrayElements(m, nullptr);
//    jfloat *Result = env->GetFloatArrayElements(result, nullptr);
//    for (int i = 0; i < 4; i++) {
//        float s = 0.0f;
//        for (int j = 0; j < 4; j++) {
//            s += T[j * 4 + i] * M[j];
//        }
//        Result[i] = s;
//    }
//    env->ReleaseFloatArrayElements(t, T, JNI_ABORT);
//    env->ReleaseFloatArrayElements(m, M, JNI_ABORT);
//    env->ReleaseFloatArrayElements(result, Result, 0);
//}
JNIEXPORT void JNICALL
Java_com_example_my3d_ui_theme_canvas_CppFuns_00024Companion_transformPoints(JNIEnv *env, jobject thiz,
                                                              jfloat valueX, jfloat valueY,
                                                              jobjectArray points) {
    jsize len = (*env).GetArrayLength(points);
    Matrix_4_4 matrixRotateOx = rotateOx(valueY/propX);
    Matrix_4_4 matrixRotateOy = rotateOy(valueX/propY);
    Matrix_4_4 transformMatrix = multMatrix(matrixRotateOx,matrixRotateOy);
    for (int i = 0; i < len; i++) {
        jobject point = (*env).GetObjectArrayElement(points, i);
        float x = (*env).GetFloatField(point, fid_x);
        float y = (*env).GetFloatField(point, fid_y);
        float z = (*env).GetFloatField(point, fid_z);
        Array_4 arr = mult(transformMatrix,{x,y,z,1.0f});
        x = arr.arr[0];
        y = arr.arr[1];
        z = arr.arr[2];
        (*env).SetFloatField(point,fid_x,x);
        (*env).SetFloatField(point,fid_y,y);
        (*env).SetFloatField(point,fid_z,z);

        (*env).DeleteLocalRef(point);
    }
}
}