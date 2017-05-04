# simple makefile for compiling java classes
#

# define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
#
JFLAGS = -g

# typing 'make' will invoke the first target entry in the makefile 
# (the default one in this case)
#
default: Heap.class FourWayHeap.class encoder.class decoder.class

# this target entry builds the class files
# and the rule associated with this entry gives the command to create it
#
Heap.class: Heap.java
        $(JCC) $(JFLAGS) Heap.java
FourWayHeap.class: FourWayHeap.java
        $(JCC) $(JFLAGS) FourWayHeap.java
encoder.class: encoder.java
        $(JCC) $(JFLAGS) encoder.java
decoder.class: decoder.java
        $(JCC) $(JFLAGS) decoder.java

# To start over from scratch, type 'make clean'.  
# Removes all .class files, so that the next make rebuilds them
#
clean:
        $(RM) *.class

