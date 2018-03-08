public class Validation
{
	private String mail;
	private String password;
	private String number;
	private String username;
	public boolean emailValidation(String mail)
	{
		this.mail = mail;
		boolean validation=true;
		String invalidChar="(.*[,~,!,#,$,%,^,&,(,),-,=,+,[,{,],},|,;,:,<,>,/,,,?].*$)";
		String num = "(.*[0-9].*)";
		int error=0;
		if(mail.indexOf('@')!=mail.lastIndexOf('@')||mail.indexOf('@')==-1||mail.indexOf('@')==0||mail.matches(invalidChar)
			&&mail.indexOf('_')==0||mail.indexOf(num)==0)
		{
			validation=false;
		}
		int check=mail.indexOf('@');
		if(mail.indexOf('.')<check)
			if(mail.indexOf('.')==mail.lastIndexOf('.'))
			validation=false;
		check++;
		if(mail.indexOf('_')==check||mail.lastIndexOf('.')==(mail.length()-1)||mail.indexOf('_')==(mail.indexOf('@')-1))
		{
			validation=false;
		}
		return validation;
	}
	public boolean passwordValidation(String password,String username)
	{
		this.password=password;
		this.username=username;
		boolean validation=true;
			String upperChar = "(.*[A-Z].*)";
			String lowerChar = "(.*[a-z].*)";
			String num = "(.*[0-9].*)";
			String specialChar = "(.*[,~,!,@,#,$,%,^,&,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,.,,,?].*$)";
			if(password.length()>33||password.length()<7)
			{
				validation=false;
			}
			if(password.indexOf(username)>-1)
			{
				validation=false;
			}
			if (!(password.matches(upperChar)) || !(password.matches(lowerChar)) ||
					!(password.matches(num)) || !(password.matches(specialChar)))
			{
				validation=false;
			}
		return validation;
	}
	public boolean contactValidation(String number)
	{
		this.number=number;
		boolean validation=true;
		String num = "(.*[0-9].*)";
		String codeChar="(.*[+,-].*)";
		if(!(number.matches(num)||number.matches(codeChar)))
		{
			validation=false;
		}
		return validation;
	}
}
