#!/bin/bash

PACKAGE="com.appham.gimmerecipes"

adb devices
ls

echo "monkey test '$PACKAGE' on emulator:"

adb -e shell monkey --monitor-native-crashes -p $PACKAGE -v 10000

echo "finished testing '$PACKAGE' on emulator!"

sleep 10
