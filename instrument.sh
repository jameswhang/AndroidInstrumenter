java -cp soot-trunk.jar:./ soot.Main  -android-jars /Users/jameswhang/android-platforms -process-dir /Users/jameswhang/Documents/school/eecs450/project/code/AndroidInstrument/apks/org.projectvoodoo.controlapp.apk -allow-phantom-refs -d ./sootOutput -src-prec apk -output-format dex