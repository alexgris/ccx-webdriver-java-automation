# ccx-webdriver-java-automation
Test automation code using Selenium WebDriver + Java + Maven + TestNG on CCX project using Silverlight

## PROJECT DESCRIPTION
This Java-based test automation script is a fully functional code created for a real online tendering system. The source code is
not meant for installation & running. Instead it can be used as an example of ready-to-use solutions for most commonly encountered 
problems and situations that many software developers in test (SDETs) may come into while automating regression tests using
Selenium WebDriver with Java.

## 1. Using Desired Capabilities on a WebBrowser
   - [Instantiating Desired Capabilities on IE11](https://github.com/alexgris/ccx-webdriver-java-automation/blob/b29be3496fde722ede7f9d79938c885121270c9b/src/test/java/com/generics/TestUnderIEbrowser.java#L35-L42)
   - [Navigating to Desired Web-site Using setCapability](https://github.com/alexgris/ccx-webdriver-java-automation/blob/b29be3496fde722ede7f9d79938c885121270c9b/src/test/java/com/generics/TestUnderIEbrowser.java#L68)
   - [Clear the Cache, Cookies, History, and Saved Form Data for All Running Instances of IE (including those started manually)](https://github.com/alexgris/ccx-webdriver-java-automation/blob/b29be3496fde722ede7f9d79938c885121270c9b/src/test/java/com/generics/TestUnderIEbrowser.java#L71)
   - [Instantiating IE Driver with the List of Desired Capabilities](https://github.com/alexgris/ccx-webdriver-java-automation/blob/b29be3496fde722ede7f9d79938c885121270c9b/src/test/java/com/generics/TestUnderIEbrowser.java#L74-L75)
   - [Find out IE Version Number](https://github.com/alexgris/ccx-webdriver-java-automation/blob/b29be3496fde722ede7f9d79938c885121270c9b/src/test/java/com/generics/TestUnderIEbrowser.java#L83-L108)
   - [Set Preferences for FireFox Browser Profile](https://github.com/alexgris/ccx-webdriver-java-automation/blob/8e2b7f464f259ff0d2bb29b0a9c84b1118f6ad84/src/test/java/com/generics/TestUnderFFbrowser.java#L30-L40)
   - [Get FireFox Browser Name and Version through the Capabilities](https://github.com/alexgris/ccx-webdriver-java-automation/blob/8e2b7f464f259ff0d2bb29b0a9c84b1118f6ad84/src/test/java/com/generics/TestUnderFFbrowser.java#L44-L48)   

## 2. Verify Availability of Internet Connection
   - [Checking Internet Connection](https://github.com/alexgris/ccx-webdriver-java-automation/blob/5446c40ff88ca48369715500ac3ed11d18cc751a/src/test/java/com/generics/VerifyInternetConnection.java#L12-L46)
   
## 3. Locating Web Elements by XPtath
   - [Find Any Web Element with a Specified ID](https://github.com/alexgris/ccx-webdriver-java-automation/blob/1787e6af8d9293ceefa7988f0de545ed5d783e1e/src/test/java/org/project/CreateNewTenderTest.java#L120)
   - [Find Any Web Element with a Specified CLASS](https://github.com/alexgris/ccx-webdriver-java-automation/blob/1787e6af8d9293ceefa7988f0de545ed5d783e1e/src/test/java/org/project/CreateNewTenderTest.java#L201)
   - [Find Any Web Element that contains specific TEXT](https://github.com/alexgris/ccx-webdriver-C--automation/blob/f1b1101c70d6911bf0c93c87ee475554daf437a8/CCXUITestsDemo/MainTests.cs#L1434)
   - [Find Any Web Element that contains specific STYLE parameters](https://github.com/alexgris/ccx-webdriver-C--automation/blob/f1b1101c70d6911bf0c93c87ee475554daf437a8/CCXUITestsDemo/MainTests.cs#L3074)
   - [Find all descendant elements of a list](https://github.com/alexgris/ccx-webdriver-java-automation/blob/be34fa33c30682da851a58a0a49c21e28dd880c9/src/test/java/org/project/CreateNewTenderTest.java#L2288)
   - [Find all descendant rows (tr) of a table](https://github.com/alexgris/ccx-webdriver-java-automation/blob/be34fa33c30682da851a58a0a49c21e28dd880c9/src/test/java/org/project/CreateNewTenderTest.java#L1218)
   - [Find all children elements of a parent element](https://github.com/alexgris/ccx-webdriver-java-automation/blob/be34fa33c30682da851a58a0a49c21e28dd880c9/src/test/java/org/project/CreateNewTenderTest.java#L370)
   
## 4. Selenium Code Solutions
   - [Switching to iFrame by its Name](https://github.com/alexgris/ccx-webdriver-java-automation/blob/1787e6af8d9293ceefa7988f0de545ed5d783e1e/src/test/java/org/project/CreateNewTenderTest.java#L198)
   - [Switching to iFrame by its Source](https://github.com/alexgris/ccx-webdriver-C--automation/blob/f1b1101c70d6911bf0c93c87ee475554daf437a8/CCXUITestsDemo/MainTests.cs#L231)
   - [Switching to iFrame by its TagName and Number](https://github.com/alexgris/ccx-webdriver-C--automation/blob/f1b1101c70d6911bf0c93c87ee475554daf437a8/CCXUITestsDemo/MainTests.cs#L275)
   - [Switching to iFrame by its ID](https://github.com/alexgris/ccx-webdriver-C--automation/blob/f1b1101c70d6911bf0c93c87ee475554daf437a8/CCXUITestsDemo/MainTests.cs#L4008)
   - [Exiting from All iFrames](https://github.com/alexgris/ccx-webdriver-java-automation/blob/1787e6af8d9293ceefa7988f0de545ed5d783e1e/src/test/java/org/project/CreateNewTenderTest.java#L204)
   - [Collecting Web Elements in a List](https://github.com/alexgris/ccx-webdriver-java-automation/blob/be34fa33c30682da851a58a0a49c21e28dd880c9/src/test/java/org/project/CreateNewTenderTest.java#L613)
   - [Addressing Every Web Element in a List](https://github.com/alexgris/ccx-webdriver-java-automation/blob/be34fa33c30682da851a58a0a49c21e28dd880c9/src/test/java/org/project/CreateNewTenderTest.java#L989-L1016)

## 5. Java Code Solutions
   - [Deleting a File](https://github.com/alexgris/ccx-webdriver-java-automation/blob/1787e6af8d9293ceefa7988f0de545ed5d783e1e/src/test/java/org/project/CreateNewTenderTest.java#L49-L63)
   
