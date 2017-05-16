import os
import glob

apks = glob.glob('./*.apk')

for apk in apks:
    os.system("jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore my-release-key.keystore {} alias_name".format(apk))
