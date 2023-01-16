
default:
	@echo " "
	@echo "make target"
	@echo "available targets: clean, mar04, example"
	@echo " "

clean:
	rm -f Lexer.java Main.java Tokens.java  ParentRDP.java
	rm -f *.class


# a bit "clunky" to deal with dependence between
# concrete lexer and concrete tokens
# since concrete tokens gets created by rdp_generator

mar04: 
	rm -f Mar04_Lexer.class Mar04_Tokens.* Mar04_RDP.*
	javac RDP_Generator.java
	-java RDP_Generator Mar04.g
#	rm -f Lexer.java Main.java Tokens.java ParentRDP.java Mar04_RDP.java *.class
	javac Mar04_Lexer.java
	javac RDP_Generator.java
	java RDP_Generator Mar04.g
	javac *.java
	java RDP_Generator Mar04.g

example:
	rm -f Example_Lexer.class Example_Tokens.* Example_RDP.*
	javac RDP_Generator.java
	-java RDP_Generator Example.g
#	rm -f Lexer.java Main.java Tokens.java ParentRDP.java Mar04_RDP.java *.class
#	javac Example_Tokens.java
	javac Example_Lexer.java
	javac RDP_Generator.java
	java RDP_Generator Example.g
	javac *.java
	java RDP_Generator Example.g

