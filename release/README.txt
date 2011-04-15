To convert a single file on windows just type:

	run.bat examples/ForExamples.java

To convert an entire directory and subdirectories type:

	run.bat examples

On *nix systems the above examples are:

	./run.sh examples/ForExamples.java
	./run.sh examples

When converting a directory, you can specify --preservedirs to have java2objc preserve the original directory structure:

	run.bat --preservedirs examples

By default it places all generated files directly in the output folder.

You can also specify custom indenting in the generated files.  By default two spaces are used.  You can customize it using the --indent and --indentType options, e.g. --indent=1 --indentType=tab.  --indent specifies the number of indent levels, and --indentType can be either “tab” or “space”.

Finally, if you place a file named mappings.properties near the file you’re converting it will be used for mapping Java types to Objective C types.  Edit mappings.properties in the examples directory for more information.