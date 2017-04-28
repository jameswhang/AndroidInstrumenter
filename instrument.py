import os
import glob

def run_soot(apk_name):
    os.system('java -cp soot-trunk.jar:./ soot.Main  -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/{} -allow-phantom-refs -d ./sootOutput -src-prec apk -output-format dex'.format(apk_name))


if __name__ == '__main__':
    apks = glob.glob('./apks/*.apk')
    for apk in apks:
        apk = apk.split('/')[-1]
#        print apk
        run_soot(apk)
    
