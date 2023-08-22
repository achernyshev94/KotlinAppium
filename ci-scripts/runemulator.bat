call start c:\npm\appium &
start C:\Users\adchernyshev\AppData\Local\Android\Sdk/emulator/emulator.exe -avd and80 -no-window
SET PLATFORM=mobile_web
cd C:\Develop\AppiumKotlinAutomation
mvn -Dtest=ArticleTest test