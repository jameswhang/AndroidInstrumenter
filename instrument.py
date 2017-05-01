import os
import glob

def run_soot(apk_name):
    os.system('java -cp soot-custom.jar:rt.jar AndroidInstrument  -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./sootOutput -src-prec apk -output-format dex'.format(apk_name))
#    os.system('java -cp soot-custom.jar:rt.jar AndroidInstrument  -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./sootOutput3 -src-prec apk -f jimple'.format(apk_name))



if __name__ == '__main__':
    run_soot('com.app_greenjobs.layout-400.apk')
    """
    apks = glob.glob('./apks/*.apk')
    for apk in apks:
        apk = apk.split('/')[-1]
        print apk
        run_soot(apk)
    """
