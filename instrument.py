import os
import glob

def run_soot(apk_name):
    os.system('java -cp soot-custom.jar:rt.jar AndroidInstrument  -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./sootOutput -src-prec apk -output-format dex'.format(apk_name))

def run_soot_main(apk_name, output_dir):
    os.system('java -cp soot-custom.jar:rt.jar soot.Main -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./{} -src-prec apk -f jimple'.format(apk_name, output_dir))

def run_soot_modify(apk_name, output_dir):
    os.system('java -cp soot-custom.jar:rt.jar:android.jar AndroidInstrument -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./{} -src-prec apk -f jimple'.format(apk_name, output_dir))




if __name__ == '__main__':
    #run_soot_modify('com.app_greenjobs.layout-400.apk', 'testdir')
    run_soot('com.app_greenjobs.layout-400.apk')
    #run_soot('app-debug.apk')
    #run_soot_modify('app-debug.apk', 'testdir')
    """
    apks = glob.glob('./apks/*.apk')
    for apk in apks:
        apk = apk.split('/')[-1]
        run_soot(apk)
    """
