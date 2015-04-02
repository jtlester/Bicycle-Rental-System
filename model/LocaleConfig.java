//specify package
package model;

import java.util.Locale;

public class LocaleConfig
{
	private static Locale currentLocale;

	public static void setLocale (Locale locale) {
		if(!currentLocale.equals(locale)) {
			currentLocale = locale;
		}
	}

	public static Locale currentLocale() {
		if(currentLocale == null) {
			currentLocale = new Locale("en", "US");
		}
		return currentLocale;
	}

//MAY NOT NEED THIS	
/*	private static LocaleConfig instance = null;

	private LocaleConfig() {}

	public static LocaleConfig getInstance() {
		if (instance == null) {
			instance = new LocaleConfig();
		}
		return instance;
	}
*/
}