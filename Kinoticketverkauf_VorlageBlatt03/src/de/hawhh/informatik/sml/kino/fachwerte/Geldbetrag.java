package de.hawhh.informatik.sml.kino.fachwerte;

/**
 * Ein Geldbetrag in Euro, gespeichert als ganze Euro- und ganze Cent-Beträge.
 *
 * @author Thu Thao Tran, Paulina Pansow
 */
public final class Geldbetrag {

	private final int _eurocent;
	
	private Geldbetrag(int eurocent)
	{
		_eurocent = eurocent;
	}
	
	/*
	 * Gibt den EuroAnteil des Betrags zurück
	 */
	
	public int euro()
	{
		return _eurocent / 100;
	}
	
	/*
	 * Gibt den CentAnteil des Betrags zurück
	 */
	public int cent()
	{
		return _eurocent % 100;
	}
	
	/*
	 * Gibt den Betrag zurück als Eurocent
	 */
	public int eurocent()
	{
		return _eurocent;
	}
	
	/*
	 * Gibt den Betrag zurück als Eurocent
	 * @param int euro EuroAnteil
	 * @param int cent CentAnteil
	 */
	public static Geldbetrag valueOf(int euro, int cent) {
		int eurocent = euro * 100 + cent;
		return new Geldbetrag(eurocent);
	}
	
	/*
	 * Gibt den Betrag zurück als Eurocent
	 * @param int eurocent Der Betrag als Eurocent
	 */
	public static Geldbetrag valueOf(int eurocent) {
		return new Geldbetrag(eurocent);
	}
	
	/*
	 * Addiere 2 Geldbeträge zusammen
	 * @param int geldbetrag zu addierende Geldbetrag
	 */
	public Geldbetrag add(Geldbetrag geldbetrag) {
		int eurocent = _eurocent + geldbetrag.eurocent();
		return Geldbetrag.valueOf(eurocent);
	}
	
	/*
	 * Substrahiere ein Geldbetrag von dem anderen
	 * @param int geldbetrag zu substrahierende Geldbetrag
	 */
	public Geldbetrag sub(Geldbetrag geldbetrag) {
		int eurocent = _eurocent - geldbetrag.eurocent();
		return Geldbetrag.valueOf(eurocent);
	}

	/*
	 * Multipliziere 2 Geldbeträge zusammen
	 * @param int geldbetrag zu multipliezierende Geldbetrag
	 */
	public Geldbetrag mul(int mult) {
		int eurocent = _eurocent * mult;
		return Geldbetrag.valueOf(eurocent);
	}
	
	/*
	 * Vergleiche 2 Geldbeträge miteinander
	 * @param Geldbetrag geldbetrag der zu vergleichende Gelbetrag
	 */
	public boolean kleinerGleich(Geldbetrag geldbetrag)
	{
		return _eurocent <= geldbetrag.eurocent();
	}

	/*
	 * Konvertiere eine Stringeingabe zu Geldbetrag
	 * @param String eingabe das zu konvertierende String
	 */
	public static Geldbetrag strconv(String eingabe) 
//			throws NumberFormatException
	{
//		if (morethan(1, eingabe, ',') || morethan(0, eingabe, '-'))
//		{
//			throw new NumberFormatException();
//		}
		String[] zahlen = eingabe.split("[,]");
		if (eingabe.contains(",") && zahlen.length > 1)
		{
			int euronew = Integer.parseInt(zahlen[0]);
			int centnew = 0;
			if (!zahlen[1].isEmpty())
			{
				if(zahlen[1].length()==1)
				{
					centnew = Integer.parseInt(zahlen[1])*10;
				}
				else
				{
					centnew = Integer.parseInt(zahlen[1]);
				}
			}
			return Geldbetrag.valueOf(euronew, centnew);
		}
		else
		{
			return Geldbetrag.valueOf(Integer.parseInt(eingabe) * 100);
		}
	}
	
	
	private static boolean morethan(int occ, String haystack, char needle)
	{
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (haystack.charAt(i) == needle)
			{
				count++;
			}
		}
		return count > occ;
	}
	
	@Override
	public String toString()
	{
		int euro = euro();
		int cent = cent();
		String cent1;
		if (cent() < 0)
		{
			euro = euro * -1;
			cent = cent * -1;
		}
	if ((cent() < 10 && cent() >= 0) || (cent() > -10 && cent() < 0))
		{
			cent1 = "0"+cent;
		} 
		else
		{
			cent1 = ""+cent;
		}
		return euro+","+cent1;
	}
	
	public String toBetragsstring()
	{
		return toString().replace("-", "");
	}

	/*
	 * Konvertiere einen Integer-Betrag zu Geldbetrag
	 * @param int geldbetrag der zu konvertierende Betrag
	 */
	public static Geldbetrag intconv(int geldbetrag) {
		return Geldbetrag.valueOf(geldbetrag);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof Geldbetrag) && equals((Geldbetrag) obj);
	}
	
	public boolean equals(Geldbetrag geldbetrag)
	{
		return eurocent() == geldbetrag.eurocent();
	}

}