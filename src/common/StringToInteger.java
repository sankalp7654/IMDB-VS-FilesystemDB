package common;
public class StringToInteger {
	
	public double parseInt(char number[]) {

		int pos = 2;
	    int value = 0;
	    double result ;
		for( ; number[pos]!='\0' ; pos++)
		{
			value = (value*10) + (number[pos]-'0') ;		
		}
		
		pos = pos - 2;
		result = (double) value/(Math.pow(10, pos));
		return result;	
	}
}
