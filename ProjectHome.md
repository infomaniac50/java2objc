This project provides a tool that converts Java classes to their equivalent objective C classes. Here are key differences in our approach compared to other similar tools (xmlvm is one such):

  * Works on Java source files instead of class files
  * Attempts to create well-crafted Objective C code as if it was written by hand
  * The generated code is not likely to have the exact same behavior and may not even compile. The generated code is a mere suggestion. A human should look at the generated code and tweak it automatically.

This project is still in its infancy. You can probably generate some barebone objective C classes if you directly manipulate the source-code but keep your expectations low at the moment. Any feedback or code patches are very welcome!

  * [Project Roadmap](https://sites.google.com/site/java2objc/roadmap)
  * [User Guide (temporary)](UsageGuide.md).
  * [User Guide](https://sites.google.com/site/java2objc/userguide)

Please post any questions or comments to the [java2objc Google Group](http://groups.google.com/group/java2objc).