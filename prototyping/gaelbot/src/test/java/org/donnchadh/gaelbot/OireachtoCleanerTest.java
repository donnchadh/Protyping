/**
 * 
 */
package org.donnchadh.gaelbot;

import static org.junit.Assert.*;

import org.donnchadh.gaelbot.cleaners.SimpleCleaner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Donnchadh
 *
 */
public class OireachtoCleanerTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testClean() throws Exception {
        String result = new SimpleCleaner().clean(getClass().getClassLoader().getResource("sample.html"));
        assertEquals("\r\n" + 
        		"Uimhir 32/2003: ACHT NA dTEANGACHA OIFIGIÚLA 2003 \r\n" + 
        		"&nbsp; \r\n" + 
        		"                  Baile Home           Cuardach Search           R&eacute;amhamharc Cl&oacute; Print&nbsp;Preview           Cabhair Help           Sc&aacute;ile&aacute;n Roinnte Split Screen \r\n" + 
        		"\r\n" + 
        		"        Ar Aghaidh (CUID 1 Réamhráiteach agus Ginearálta) \r\n" + 
        		"32\r\n" + 
        		" 2003\r\n" + 
        		" \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"Uimhir  32  de  2003. \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ACHT NA dTEANGACHA OIFIGIÚLA 2003 \r\n" + 
        		"\r\n" + 
        		"RIAR NA nALT \r\n" + 
        		"CUID 1 \r\n" + 
        		"Réamhráiteach agus Ginearálta \r\n" + 
        		"\r\n" + 
        		"Alt \r\n" + 
        		"\r\n" + 
        		"1. \r\n" + 
        		"Gearrtheideal agus tosach feidhme. \r\n" + 
        		"\r\n" + 
        		"2. \r\n" + 
        		"Léiriú. \r\n" + 
        		"\r\n" + 
        		"3. \r\n" + 
        		"\r\n" + 
        		"Caiteachais. \r\n" + 
        		"\r\n" + 
        		"4. \r\n" + 
        		"Rialacháin. \r\n" + 
        		"CUID 2 \r\n" + 
        		"Orgain Stáit \r\n" + 
        		"\r\n" + 
        		"5. \r\n" + 
        		"Tuarascáil bhliantúil do Thithe an Oireachtais. \r\n" + 
        		"\r\n" + 
        		"6. \r\n" + 
        		"Úsáid na dteangacha oifigiúla i dTithe an Oireachtais. \r\n" + 
        		"\r\n" + 
        		"7. \r\n" + 
        		"Achtanna an Oireachtais. \r\n" + 
        		"\r\n" + 
        		"8. \r\n" + 
        		"Riaradh an cheartais. \r\n" + 
        		"CUID 3 \r\n" + 
        		"Comhlachtaí Poiblí \r\n" + 
        		"\r\n" + 
        		"9. \r\n" + 
        		"Dualgas comhlachtaí poiblí na teangacha oifigiúla a úsáid ar stáiseanóireacht oifigiúil, etc. \r\n" + 
        		"\r\n" + 
        		"10. \r\n" + 
        		"Dualgas comhlachtaí poiblí doiciméid áirithe a fhoilsiú go comhuaineach sa dá theanga oifigiúla. \r\n" + 
        		"\r\n" + 
        		"11. \r\n" + 
        		"Úsáid na dteangacha oifigiúla ag comhlachtaí poiblí. \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"12. \r\n" + 
        		"An tAire d\'fhoilsiú treoirlínte. \r\n" + 
        		"\r\n" + 
        		"13. \r\n" + 
        		"Comhlacht poiblí d\'ullmhú dréacht-scéime. \r\n" + 
        		"\r\n" + 
        		"14. \r\n" + 
        		"An tAire do dhaingniú dréacht-scéimeanna. \r\n" + 
        		"\r\n" + 
        		"15. \r\n" + 
        		"\r\n" + 
        		"Athbhreithniú tréimhsiúil ar scéimeanna. \r\n" + 
        		"\r\n" + 
        		"16. \r\n" + 
        		"Scéimeanna a leasú. \r\n" + 
        		"\r\n" + 
        		"17. \r\n" + 
        		"Mainneachtain dréacht-scéim a ullmhú. \r\n" + 
        		"\r\n" + 
        		"18. \r\n" + 
        		"Dualgas scéimeanna a chur i gcrích. \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"19. \r\n" + 
        		"Toirmeasc ar chomhlachtaí poiblí d\'fhorchur muirear. \r\n" + 
        		"CUID 4 \r\n" + 
        		"An Coimisinéir Teanga \r\n" + 
        		"\r\n" + 
        		"20. \r\n" + 
        		"Oifig Choimisinéir na dTeangacha Oifigiúla a bhunú. \r\n" + 
        		"\r\n" + 
        		"21. \r\n" + 
        		"\r\n" + 
        		"Feidhmeanna an Choimisinéara. \r\n" + 
        		"\r\n" + 
        		"22. \r\n" + 
        		"Cumhachtaí an Choimisinéara. \r\n" + 
        		"\r\n" + 
        		"23. \r\n" + 
        		"Imscrúduithe a sheoladh. \r\n" + 
        		"\r\n" + 
        		"24. \r\n" + 
        		"Eisiamh. \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"25. \r\n" + 
        		"Faisnéis a nochtadh. \r\n" + 
        		"\r\n" + 
        		"26. \r\n" + 
        		"Tuarascáil ar fhionnachtana. \r\n" + 
        		"\r\n" + 
        		"27. \r\n" + 
        		"Scéimeanna cúitimh. \r\n" + 
        		"\r\n" + 
        		"28. \r\n" + 
        		"\r\n" + 
        		"Achomhairc chuig an Ard-Chúirt. \r\n" + 
        		"\r\n" + 
        		"29. \r\n" + 
        		"An Coimisinéir d\'fhoilsiú tráchtaireachtaí maidir le feidhm phraiticiúil, etc. an Achta. \r\n" + 
        		"\r\n" + 
        		"30. \r\n" + 
        		"Tuarascálacha ón gCoimisinéir. \r\n" + 
        		"CUID 5 \r\n" + 
        		"Logainmneacha \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"31. \r\n" + 
        		"Mínithe. \r\n" + 
        		"\r\n" + 
        		"32. \r\n" + 
        		"Orduithe logainmneacha. \r\n" + 
        		"\r\n" + 
        		"33. \r\n" + 
        		"Forléiriú focal i ndoiciméid dhlíthiúla. \r\n" + 
        		"\r\n" + 
        		"34. \r\n" + 
        		"\r\n" + 
        		"Leasú ar an Acht um Shuirbhéireacht Ordanáis Éireann 2001. \r\n" + 
        		"\r\n" + 
        		"35. \r\n" + 
        		"Aisghairm. \r\n" + 
        		"CUID 6 \r\n" + 
        		"Ilghnéitheach \r\n" + 
        		"\r\n" + 
        		"36. \r\n" + 
        		"Ról an Ombudsman. \r\n" + 
        		"AN CHÉAD SCEIDEAL \r\n" + 
        		"Comhlachtaí Poiblí \r\n" + 
        		"AN DARA SCEIDEAL \r\n" + 
        		"An Coimisinéir Teanga \r\n" + 
        		"Na hAchtanna dá dTagraítear \r\n" + 
        		"An tAcht um Chomhaontú na Breataine-na hÉireann 1999 \r\n" + 
        		"1999, Uimh. 1 \r\n" + 
        		"Acht Choimisinéirí na Stát-Sheirbhíse 1956 \r\n" + 
        		"1956, Uimh. 45 \r\n" + 
        		"Acht Rialuithe na Stát-Sheirbhíse 1956 \r\n" + 
        		"\r\n" + 
        		"1956, Uimh. 46 \r\n" + 
        		"Achtanna Rialaithe na Státseirbhíse 1956 go 1996 \r\n" + 
        		"An tAcht um Chosaint Sonraí 1988 \r\n" + 
        		"1988, Uimh. 25 \r\n" + 
        		"An tAcht um Thoghcháin do Thionól na hEorpa 1977 \r\n" + 
        		"1977, Uimh. 30 \r\n" + 
        		"An tAcht um Thoghcháin do Pharlaimint na hEorpa 1993 \r\n" + 
        		"1993, Uimh. 30 \r\n" + 
        		"An tAcht Cuanta 1946 \r\n" + 
        		"\r\n" + 
        		"1946, Uimh. 9 \r\n" + 
        		"An tAcht Cuanta 1996 \r\n" + 
        		"1996, Uimh. 11 \r\n" + 
        		"An tAcht Rialtais Áitiúil 2001 \r\n" + 
        		"2001, Uimh. 37 \r\n" + 
        		"Marriages (Ireland) Act  1844 \r\n" + 
        		"6 &amp; 7 Vict., c. 81 \r\n" + 
        		"An tAcht Airí agus Rúnaithe 1924 \r\n" + 
        		"1924, Uimh. 16 \r\n" + 
        		"An tAcht Airí agus Rúnaithe (Leasú) 1956 \r\n" + 
        		"1956, Uimh. 21 \r\n" + 
        		"An tAcht Ombudsman 1980 \r\n" + 
        		"\r\n" + 
        		"1980, Uimh. 26 \r\n" + 
        		"An Acht um Shuirbhéireacht Ordanáis Éireann 2001 \r\n" + 
        		"2001, Uimh. 43 \r\n" + 
        		"An tAcht Logainmneacha (Foirmeacha Gaeilge) 1973 \r\n" + 
        		"1973, Uimh. 24 \r\n" + 
        		"\r\n" + 
        		"An tAcht um Bainistíocht na Seirbhíse Poiblí 1997 \r\n" + 
        		"1997, Uimh. 27 \r\n" + 
        		"Na hAchtanna um Binsí Fiosrúcháin (Fianaise) 1921 go 2002 \r\n" + 
        		"Uimhir  32  de  2003 \r\n" + 
        		"ACHT NA dTEANGACHA OIFIGIÚLA 2003 \r\n" + 
        		"\r\n" + 
        		"ACHT CHUN ÚSÁID NA GAEILGE A CHUR CHUN CINN CHUN CRÍOCH OIFIGIÚIL SA STÁT; CHUN SOCRÚ A DHÉANAMH MAIDIR LE DHÁ THEANGA OIFIGIÚLA AN STÁIT A ÚSÁID IN IMEACHTAÍ PARLAIMINTE, IN ACHTANNA AN OIREACHTAIS, I RIARADH AN CHEARTAIS, LE LINN CUMARSÁID A DHÉANAMH LEIS AN bPOBAL NÓ SEIRBHÍSÍ A SHOLÁTHAR DON PHOBAL AGUS LE LINN OBAIR COMHLACHTAÍ POIBLÍ A DHÉANAMH; CHUN DUALGAIS COMHLACHTAÍ DEN SÓRT SIN I LEITH THEANGA - CHA OIFIGIÚLA AN STÁIT A LEAGAN AMACH; AGUS CHUN NA gCRÍOCH SIN, CHUN SOCRÚ A DHÉANAMH MAIDIR LE BUNÚ OIFIG CHOIMISINÉIR NA dTEANGACHA OIFIGIÚLA AGUS CHUN A FEIDHMEANNA A MHÍNIÚ; CHUN SOCRÚ A DHÉANAMH MAIDIR LEIS AN gCOIMISINÉIR D\'FHOILSIÚ FAISNÉIS ÁIRITHE A BHAINEANN LE CRÍOCHA AN ACHTA SEO; AGUS CHUN SOCRÚ A DHÉANAMH I dTAOBH NITHE GAOLMHARA. [14  Iúil , 2003] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"ACHTAÍTEAR AG AN OIREACHTAS MAR A LEANAS: \r\n" + 
        		"", result);
    }
    
    @Test
    public void testClean2() throws Exception {
        String result = new SimpleCleaner().clean(getClass().getClassLoader().getResource("sample2.html"));
        String expected = "\r\n" + 
        		"Uimhir 32/2003: CUID 1 Réamhráiteach agus Ginearálta \r\n" + 
        		"&nbsp; \r\n" + 
        		"                  Baile Home           Cuardach Search           R&eacute;amhamharc Cl&oacute; Print&nbsp;Preview           Cabhair Help           Sc&aacute;ile&aacute;n Roinnte Split Screen \r\n" + 
        		"\r\n" + 
        		"        An Chéad Lch. \r\n" + 
        		"Lch. Roimhe Seo (ACHT NA dTEANGACHA OIFIGIÚLA 2003) \r\n" + 
        		"Ar Aghaidh (CUID 2 Orgain Stáit) \r\n" + 
        		"\r\n" + 
        		"32\r\n" + 
        		" 2003\r\n" + 
        		" ACHT NA dTEANGACHA OIFIGIÚLA 2003 \r\n" + 
        		"[EN] \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"CUID 1 \r\n" + 
        		" Réamhráiteach agus Ginearálta \r\n" + 
        		"  [EN] \r\n" + 
        		"Gearrtheideal agus tosach feidhme. \r\n" + 
        		"\r\n" + 
        		"1. \r\n" + 
        		"\r\n" + 
        		"&mdash;(1) Féadfar Acht na dTeangacha Oifigiúla 2003 a ghairm den Acht seo. \r\n" + 
        		"  [EN] \r\n" + 
        		"\r\n" + 
        		"(2) Tiocfaidh an tAcht seo i ngníomh cibé lá nó laethanta, nach déanaí ná 3 bliana tar éis an tAcht seo a rith, a shocrófar chuige sin, le hordú nó le horduithe ón Aire faoin alt seo, i gcoitinne nó faoi threoir aon chríche nó forála áirithe agus féadfar laethanta éagsúla a shocrú amhlaidh chun críoch éagsúil agus le haghaidh forálacha éagsúla. \r\n" + 
        		"[EN] \r\n" + 
        		"Léiriú. \r\n" + 
        		"\r\n" + 
        		"2. \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"&mdash;(1) San Acht seo, ach amháin mar a n-éilíonn an comhthéacs a mhalairt&mdash; \r\n" + 
        		"  [EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;Coimisinéir&#8221;, de réir mar a éilíonn an comhthéacs, Oifig Choimisinéir na dTeangacha Oifigiúla a bhunaítear le  halt 20  nó sealbhóir na hoifige sin de thuras na huaire; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"folaíonn &#8220;cúirt&#8221; binse arna bhunú faoi na hAchtanna um Binsí Fiosrúcháin (Fianaise) 1921 go 2002; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;dréacht-scéim&#8221; dréacht-scéim a bheidh le hullmhú ag comhlacht poiblí faoin Acht seo; \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;achtachán&#8221; reacht nó ionstraim arna déanamh faoi chumhacht a thugtar le reacht; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"folaíonn &#8220;feidhmeanna&#8221; cumhachtaí agus dualgais, agus aon tagairtí do chomhlíonadh feidhmeanna folaíonn siad, maidir le cumhachtaí agus dualgais, tagairtí d\'fheidhmiú na gcumhachtaí agus do chomhall na ndualgas; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;limistéar Gaeltachta&#8221; limistéar a mbeidh cinnte de thuras na huaire le hordú arna dhéanamh faoi alt 2 den Acht Airí agus Rúnaithe (Leasú) 1956 gur limistéar Gaeltachta é; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;ceann&#8221; ceann comhlachta phoiblí; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;ceann&#8221; comhlachta phoiblí&#8221;&mdash; \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( a ) i ndáil le Roinn Stáit, an tAire den Rialtas atá i bhfeighil na Roinne sin, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( b ) i ndáil le hOifig an Ard-Aighne, an tArd-Aighne, \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( c ) i ndáil le hOifig Choimisinéirí na Státseirbhíse, Coimisinéirí na Státseirbhíse, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( d ) i ndáil le hOifig an Ard-Reachtaire Cuntas agus Ciste, an tArd-Reachtaire Cuntas agus Ciste, \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( e ) i ndáil le hOifig an Stiúrthóra Ionchúiseamh Poiblí, an Stiúrthóir Ionchúiseamh Poiblí, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( f ) i ndáil le hOifig Thithe an Oireachtais, Cathaoirleach Dháil Éireann, \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( g ) i ndáil le hOifig an Choimisinéara Faisnéise, an Coimisinéir Faisnéise, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( h ) i ndáil le hOifig na gCoimisinéirí um Cheapacháin Áitiúla, na Coimisinéirí um Cheapacháin Áitiúla, \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( i ) i ndáil le hOifig an Ombudsman, an tOmbudsman, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( j ) i ndáil le haon chomhlacht poiblí eile, an duine atá i seilbh, nó a chomhlíonann feidhmeanna, oifig phríomhoifigeach feidhmiúcháin (cibé ainm a thugtar uirthi) an chomhlachta; \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"tá le &#8220;údarás áitiúil&#8221; an bhrí a shanntar dó le fo-alt (1) d\'alt 2 den Acht Rialtais Áitiúil 2001; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;an tAire&#8221; an tAire Gnóthaí Pobail, Tuaithe agus Gaeltachta; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;na teangacha oifigiúla&#8221; an Ghaeilge (ós í an teanga náisiúnta agus an phríomhtheanga oifigiúil í) agus an Béarla (ós teanga oifigiúil eile é) mar a shonraítear in Airteagal 8 den Bhunreacht; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;forordaithe&#8221; forordaithe ag an Aire le rialacháin faoi  \r\n" + 
        		"alt 4 \r\n" + 
        		"; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;imeachtaí&#8221; imeachtaí sibhialta nó coiriúla os comhair aon chúirte; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"déanfar &#8220;comhlacht poiblí&#8221; a fhorléiriú de réir an  Chéad Sceidil ; \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"folaíonn &#8220;taifead&#8221; aon mheabhrán, leabhar, plean, léarscáil, líníocht, léaráid, saothar pictiúrtha nó grafach nó doiciméad eile, aon ghrianghraf, scannán nó taifeadadh (cibé acu is taifeadadh fuaime nó taifeadadh íomhánna nó iad araon é), aon fhoirm ina gcoimeádtar sonraí (de réir bhrí an Achta um Chosaint Sonraí 1988), aon fhoirm eile (lena n-áirítear foirm mheaisín-inléite) nó rud eile ina ndéantar faisnéis a choimeád nó a stóráil de láimh, go meicniúil nó go leictreonach agus aon rud ar cuid nó cóip é, i bhfoirm ar bith, d\'aon cheann díobh sin roimhe seo nó ar teaglaim é de dhá cheann nó níos mó díobh sin roimhe seo; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;scéim&#8221; scéim arna daingniú ag an Aire faoi  \r\n" + 
        		"alt 14 \r\n" + 
        		"; \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"ciallaíonn &#8220;seirbhís&#8221; seirbhís arna tairiscint nó arna soláthar (cibé acu go díreach nó go neamhdhíreach) ag comhlacht poiblí don phobal i gcoitinne nó d\'aicme den phobal i gcoitinne. \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(2) ( a ) San Acht seo aon tagairt d\'alt nó do sceideal is tagairt í d\'alt den Acht seo nó do Sceideal a ghabhann leis an Acht seo, mura gcuirtear in iúl gur tagairt d\'achtachán éigin eile atá beartaithe. \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( b ) San Acht seo aon tagairt d\'fho-alt nó do mhír nó d\'fhomhír is tagairt í don fho-alt nó don mhír nó don fhomhír den fhoráil ina bhfuil an tagairt, mura gcuirtear in iúl gur tagairt d\'fhoráil éigin eile atá beartaithe. \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"Caiteachais. \r\n" + 
        		"\r\n" + 
        		"3. \r\n" + 
        		"\r\n" + 
        		"&mdash;Déanfar na caiteachais a thabhóidh an tAire agus aon Aire eile den Rialtas ag riaradh an Achta seo a íoc, a mhéid a cheadóidh an tAire Airgeadais é, as airgead a sholáthróidh an tOireachtas. \r\n" + 
        		"  [EN] \r\n" + 
        		"Rialacháin. \r\n" + 
        		"\r\n" + 
        		"4. \r\n" + 
        		"\r\n" + 
        		"&mdash;(1) Féadfaidh an tAire, le toiliú an Aire Airgeadais&mdash; \r\n" + 
        		"  [EN] \r\n" + 
        		"\r\n" + 
        		"\r\n" + 
        		"( a ) le rialacháin, foráil a dhéanamh, faoi réir fhorálacha an Achta seo, le haghaidh aon ní dá dtagraítear san Acht seo mar ní atá forordaithe nó le forordú, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( b ) i dteannta aon chumhachta eile a thugtar dó nó di chun rialacháin a dhéanamh, rialacháin a dhéanamh i gcoitinne chun críocha an Achta seo agus chun lánéifeacht a thabhairt don Acht seo, \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( c ) más rud é, le linn na chéad 3 bliana d\'fheidhm an Achta seo maidir le comhlacht poiblí a shonraítear i  bhfomhír (3), (4)  nó  (5)  de  mhír 1  den  Chéad Sceideal , go n-éireoidh aon deacracht i ndáil leis an Acht seo a thabhairt i ngníomh a mhéid atá feidhm aige maidir leis an  gcomhlacht sin, aon ní a dhéanamh le rialacháin ar dealraitheach gur gá nó gur fóirsteanach é chun an tAcht seo a thabhairt i ngníomh a mhéid atá feidhm aige maidir leis an gcomhlacht sin agus féadfar, le rialacháin faoin mír seo, a mhéid amháin is dealraitheach gur gá é chun na rialacháin a thabhairt in éifeacht, foráil den Acht seo a mhodhnú má tá an modhnú i gcomhréir le críocha, prionsabail agus meon an Achta seo, agus \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"( d ) más rud é, in aon slí eile, go n-éireoidh aon deacracht le linn na tréimhse 3 bliana ó thosach feidhme an Achta seo i ndáil leis an Acht seo a thabhairt i ngníomh, aon ní a dhéanamh le rialacháin ar dealraitheach gur gá nó gur fóirsteanach é chun an tAcht seo a thabhairt i ngníomh agus féadfar, le rialacháin faoin mír seo, a mhéid amháin is dealraitheach gur gá é chun na rialacháin a thabhairt in éifeacht, foráil den Acht seo a mhodhnú má tá an modhnú i gcomhréir le críocha, prionsabail agus meon an Achta seo. \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(2) Féadfaidh cibé forálacha teagmhasacha, forlíontacha agus iarmhartacha a bheith i rialacháin faoin Acht seo is dóigh leis an Aire a bheith riachtanach nó fóirsteanach chun críocha na rialachán. \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(3) I gcás ina mbeartaíonn an tAire rialacháin a dhéanamh faoi  mhír (c)  nó  (d) d\'fho-alt (1)  nó chun críocha  mhír 1(5) , nó faoi  mhír 3 , den  Chéad Sceideal , cuirfidh sé nó sí faoi deara dréacht de na rialacháin a leagan faoi bhráid gach Tí den Oireachtas agus ní dhéanfar na rialacháin go dtí go mbeidh rún ag ceadú an dréachta rite ag gach Teach acu sin. \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(4) I gcás ina mbeartaíonn an tAire rialacháin a dhéanamh faoi  fho-alt (1)(c) , rachaidh sé nó sí, sula ndéanfaidh sé nó sí amhlaidh, i gcomhairle le cibé Aire eile (más ann) den Rialtas is cuí leis an Aire ag féachaint d\'fheidhmeanna an Aire eile sin den Rialtas i ndáil leis na rialacháin atá beartaithe. \r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(5) Féadfar, le rialacháin lena bhforordaítear comhlacht, eagraíocht nó grúpa (&#8220;an comhlacht&#8221;) chun críocha  mhír 1 (5)  den  Chéad Sceideal , a fhoráil nach mbeidh feidhm ag an Acht seo maidir leis an gcomhlacht ach amháin i leith feidhmeanna sonraithe de chuid an chomhlachta, agus beidh feidhm ag an Acht seo agus beidh éifeacht leis de réir aon fhorála den sórt sin. \r\n" + 
        		"\r\n" + 
        		"[EN] \r\n" + 
        		"\r\n" + 
        		"(6) Déanfar gach rialachán faoin Acht seo (seachas rialachán dá dtagraítear i  bhfo-alt (3) ) a leagan faoi bhráid gach Tí den Oireachtas a luaithe is féidir tar éis a dhéanta agus má dhéanann ceachtar Teach acu sin, laistigh den 21 lá a shuífidh an Teach sin tar éis an rialachán a leagan faoina bhráid, rún a rith ag neamhniú an rialacháin, beidh an rialachán ar neamhní dá réir sin, ach sin gan dochar do bhailíocht aon ní a rinneadh roimhe sin faoin rialachán. \r\n" + 
        		"";
        assertEquals(expected, result);
    }
}
