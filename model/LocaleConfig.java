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
}
