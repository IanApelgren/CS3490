package net.dtcc.lib;

public class AllInOne {

	public static void main(String[] args) {
		

	}//end main
	
	/////Areas/////
	public double AreaOfRectange(double length, double width) {
		return(length*width);
	}
	public double AreaOfSquare(double length) {
		return(length*length);
	}
	public double AreaOfTriangle(double base, double height) {
		return((base*height)/2);
	}
	public double AreaOfCircle(double radius) {
		return(Math.PI*(radius*radius));
	}
	public double AreaOfTrapazoid(double base1,double base2, double height) {
		return(((base1+base2)/2)*height);
	}
	public double AreaOfEllipse(double radiusSmall, double radiusLarge) {
		return(Math.PI*radiusSmall*radiusLarge);
	}
	public double AreaOfPentagon(double length) {
		return(1.72*(length*length));
	}
	public double AreaOfParallelogram(double base, double height) {
		return(base*height);
	}
	public double AreaOfRhombus(double diagonal1, double diagonal2) {
		return((diagonal1*diagonal2)/2);
	}
	public double AreaOfHexagon(double length) {
		return(2.598*(length*length));
	}
	public double AreaOfPolygon(double sides, double radius) {
		return(.5*sides*radius);
	}
	
	/////Fractions/////
	public double FractionAddition(int numerator1, int denominator1,
			int numerator2, int denominator2) {
		int denominatorTotal=denominator1*denominator2;
		int numerator1New = numerator1*denominator2;
		int numerator2New = numerator2*denominator1;
		
		return((numerator1New+numerator2New)/denominatorTotal);
	}
	public double FractionSubtraction(int numerator1, int denominator1,
			int numerator2, int denominator2) {
		int denominatorTotal=denominator1*denominator2;
		int numerator1New = numerator1*denominator2;
		int numerator2New = numerator2*denominator1;
		
		return((numerator1New-numerator2New)/denominatorTotal);
	}
	public double FractionMultiply(int numerator1, int denominator1,
			int numerator2, int denominator2) {
		int denominatorTotal=denominator1*denominator2;
		
		return((numerator1*numerator2)/denominatorTotal);
	}
	public double FractionDivide(int numerator1, int denominator1,
			int numerator2, int denominator2) {
		
		return((numerator1*denominator2)/(denominator1*numerator2));
	}
	
	/////Binary/////
	public int BinaryConverter(String binary) {
		int counter = 0;
		for(int i=binary.length(); i>0; i--) {
			String letter =Character.toString(binary.charAt(i));
			if (letter=="1"){
				counter+=Math.pow(2, binary.length()-i);
			}//end if
			else {
				counter+=0;
			}
		}//end for
		return(counter);
	}
	
	// Temperature:
	public double celciusToFarenhiet(double celcius) 
	{
		return (9/5)*celcius+32;
	}	
	
	public double celciusToKelvin(double celcius) 
	{
		return celcius+273.15;
	}	
	
	public double farenhietToCelcius(double farenhiet) 
	{
		return (5/9)*(farenhiet-32);
	}	
	
	public double farenhietToKelvin(double farenhiet) 
	{
		return (5/9)*(farenhiet-32)+273.15;
	}
	
	
	// Compute the volume of given objects
	public double volumeOfCube(double length) 
	{
		return Math.pow(length, 3);
	}
	
	public double volumeOfBox(double length, double width, double height) 
	{
		return length*width*height;
	}
	
	public double volumeOfCylinder(double radius, double height) 
	{
		return Math.PI*radius*radius*height;
	}
	
	public double volumeOfCone(double radius, double height) 
	{
		return (1/3)*Math.PI*radius*radius*height;
	}
	
	public double volumeOfShere(double radius) 
	{
		return (4/3)*Math.PI*Math.pow(radius, 3);
	}
	
	
	//Compute the perimeter of given objects
	public double perimeterOfSquare(double length) 
	{
		return 4*length;
	}
	
	public double perimeterOfSquare(double length, double width) 
	{
		return 2*(length+width);
	}
	
	//Circumference
	public double circumferenceOfCicle(double radius) 
	{
		return 2*Math.PI*radius;
	}
	
	//Pythagorus Theorem:
	public double pythagorusTheorem(double a, double b) 
	{
		return Math.pow(a, 2)+Math.pow(a, 2);
	}
}


