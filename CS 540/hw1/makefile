# compiler and compiler flag variables
JFLAGS = -g
JC = javac

# Clear any default targets for building .class files from .java files;
.SUFFIXES: .java .class

# Here is our target entry rule for creating .class files from .java files
.java.class:
	$(JC) $(JFLAGS) $*.java

# modify this macro to include all your .java files
CLASSES = \
	HW1.java \

# the default make target entry
default: classes

# We are replacing the suffix .java of all words in the macro CLASSES
# with the .class suffix
classes: $(CLASSES:.java=.class)

#RM is a predefined macro in make (RM = rm -f)
clean:
	$(RM) *.class
