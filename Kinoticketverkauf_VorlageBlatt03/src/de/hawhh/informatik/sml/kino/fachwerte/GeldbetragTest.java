package de.hawhh.informatik.sml.kino.fachwerte;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author SE2-Team, PM2-Team
 * @version SoSe 2018
 * 
 */


public class GeldbetragTest 
{
	Geldbetrag fiftyFiveEuros = Geldbetrag.valueOf(55,0);
	Geldbetrag fiftyEuros = Geldbetrag.valueOf(50,0);
	Geldbetrag fiveEuros = Geldbetrag.valueOf(5,0);
	Geldbetrag sixEurosFourteenCent = Geldbetrag.valueOf(6,14);

	@Test
	public void stringTest() {
		assertEquals("55,00",fiftyFiveEuros.toString());
	}
	
	@Test
	public void addTest() 
	{
		assertEquals(fiftyFiveEuros,fiftyEuros.add(fiveEuros));
		assertEquals(Geldbetrag.valueOf(7, 04),sixEurosFourteenCent.add(Geldbetrag.valueOf(0, 90)));
	}
	
	@Test
	public void subTest() 
	{
		assertEquals(fiftyEuros,fiftyFiveEuros.sub(fiveEuros));
		assertEquals(Geldbetrag.valueOf(5, 99),sixEurosFourteenCent.sub(Geldbetrag.valueOf(0, 15)));
	}
	
	@Test
	public void mulTest() 
	{
		assertEquals(fiftyEuros, fiveEuros.mul(10));
	}
	
	@Test
	public void stringConvert() 
	{
		assertTrue(sixEurosFourteenCent.equals(Geldbetrag.strconv("6,14")));
	}
	
	@Test
	public void intConvert() 
	{
		assertTrue(sixEurosFourteenCent.equals(Geldbetrag.intconv(614)));
	}

}