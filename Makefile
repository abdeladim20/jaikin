all: compile

compile:
	# This command compiles all files ending in .java
	javac *.java -d build


# tell 'make' to always compile first before running.
run: compile
	# This command runs the main class
	java -cp build Main


clean:
	# This command deletes the compiled files
	rm -rf build

# Declares that these are command names, not files
.PHONY: all compile run clean