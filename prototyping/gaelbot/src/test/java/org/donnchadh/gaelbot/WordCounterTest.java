package org.donnchadh.gaelbot;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WordCounterTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCountWords() throws Exception {
        String sample = "\r\n" + 
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
        Map<String, Integer> result = new WordCounter().countWords(sample);
        assertEquals(406, result.size());
        assertEquals("{tosach=1, ghairm=1, leith=1, ós=2, leasú=1, teaglaim=1, déanfar=3, bhráid=3, thugtar=3, doiciméad=1, phoiblí=2, search=1, éifeacht=3, dhéanann=1, comhthéacs=2, saothar=1, iad=1, thuras=2, deacracht=2, taire=8, éagsúla=2, aghaidh=1, dá=3, bhfomhír=1, bith=1, leis=10, ón=1, go=9, oifig=2, rialtais=1, ghabhann=1, de=11, éagsúil=1, dóigh=1, di=1, ionstraim=1, ciallaíonn=12, aige=2, do=6, mar=4, f=1, g=1, d=3, e=1, b=3, thithe=1, c=4, gcás=2, a=60, comhair=1, n=1, bhunreacht=1, j=1, h=1, i=36, mhéid=5, scannán=1, dhéanamh=8, r=1, chumhacht=1, sin=15, mhalairt=1, tiocfaidh=1, deara=1, en=45, tagairt=7, bhailíocht=1, dtagraítear=2, binsí=1, cuirfidh=1, lch=2, chuid=1, ann=1, sceidil=1, níos=1, údarás=1, sula=1, bhforordaítear=1, beidh=3, roinn=1, choimisinéirí=1, roimhe=4, éigin=2, leabhar=1, ainm=1, cl=1, neamhniú=1, hachtanna=1, hoifige=1, mhodhnú=2, lena=2, fuaime=1, prionsabail=2, choimisinéara=1, íoc=1, leictreonach=1, alt=4, eile=12, toireachtas=1, más=3, í=4, é=12, acht=22, as=1, ar=7, díreach=1, amhlaidh=2, luaithe=1, an=67, díobh=2, seo=34, baile=1, ag=11, dhéanta=1, lánéifeacht=1, ó=1, státseirbhíse=2, meon=2, slí=1, ombudsman=1, oacute=1, bhrí=2, help=1, fiosrúcháin=1, roinnte=1, ciste=2, iúl=2, nbsp=2, taifead=1, haghaidh=2, tréimhse=1, screen=1, os=1, limistéar=3, neamhdhíreach=1, dtí=1, dochar=1, orgain=1, lá=2, laethanta=2, cinnte=1, cibé=6, chúirte=1, gan=1, rinneadh=1, home=1, toiliú=1, gcomhréir=2, tairiscint=1, léaráid=1, imeachtaí=2, fhorála=1, eacute=1, faisnéise=2, uimhir=1, achta=8, dréachta=1, chomhall=1, faoina=1, roinne=1, mura=2, ngníomh=5, na=25, áitiúil=2, rún=2, huaire=2, feidhmiúcháin=1, ndéantar=1, cuí=1, ceadú=1, gá=4, ina=5, chomhlíonann=1, éilíonn=1, caiteachais=2, áitiúla=2, rialtas=4, leagan=3, hordú=2, fhoirm=2, gach=4, achtachán=1, le=36, chéad=6, comhlacht=6, chumhachta=1, dó=2, sceideal=5, gcoimeádtar=1, ionchúiseamh=2, coimisinéirí=2, láimh=1, coiriúla=1, dteannta=1, daingniú=1, cumhachtaí=2, thabhairt=7, bheidh=1, pobail=1, faisnéis=1, cathaoirleach=1, siad=1, is=8, gcumhachtaí=1, ach=3, fhoráil=2, arna=6, náisiúnta=1, feidhme=2, stiúrthóra=1, in=6, print=1, gcoimisinéirí=1, ile=1, ndéanfaidh=1, sealbhóir=1, amhamharc=1, rite=1, aacute=2, rith=2, cóip=1, fhomhír=1, neamhní=1, acu=4, rúnaithe=1, forordaithe=3, don=5, ceann=3, mheabhrán=1, tí=2, uirthi=1, cuid=4, rialacháin=19, tá=3, cheadóidh=1, dteangacha=4, sé=3, haon=1, airgeadais=2, sí=3, forála=1, cuardach=1, cuntas=2, tagairtí=2, preview=1, split=1, maidir=5, gaeltachta=3, oifigiúil=2, mír=2, grafach=1, rud=4, stáit=2, áirithe=1, shocrú=1, seilbh=1, féadfar=5, béarla=1, atá=8, éireann=1, feidhmeanna=4, mhír=7, phobal=2, ndáil=13, threoir=1, ghaeilge=1, um=4, seachas=1, líníocht=1, agus=28, tombudsman=1, éis=3, poiblí=7, soláthar=1, airí=1, forálacha=2, shocrófar=1, chuige=1, gnóthaí=1, folaíonn=4, féachaint=1, grúpa=1, déanamh=1, stiúrthóir=1, cúirt=1, gcoitinne=4, íomhánna=1, foirm=1, araon=1, sc=1, taifeadadh=3, seirbhís=2, feidhm=4, nó=41, oireachtas=2, ní=6, airgead=1, rachaidh=1, chosaint=1, gearrtheideal=1, ná=1, aire=9, críoch=1, faoin=7, foráil=3, forordú=1, chomhlíonadh=1, mó=1, riaradh=1, iarmhartacha=1, oifigiúla=5, má=3, choimeád=1, phríomhtheanga=1, halt=1, beartaithe=3, déanaí=1, pictiúrtha=1, fhorléiriú=1, choimisinéir=1, chun=11, binse=1, hullmhú=1, rialachán=6, dualgais=2, fhorálacha=1, cheapacháin=2, ghrianghraf=1, gcomhairle=1, comhlachta=2, chríche=1, san=4, bhfoirm=1, tar=3, nach=2, duine=1, sholáthróidh=1, linn=2, aon=20, chomhlachta=2, gcuirtear=2, chomhlacht=1, cabhair=1, hoifig=8, réir=6, tuaithe=1, airteagal=1, fóirsteanach=3, sonraí=2, sonraithe=1, bhfuil=1, den=20, teanga=2, plean=1, teagmhasacha=1, gur=9, teangacha=1, thosach=1, shonraítear=2, scéim=2, ceachtar=1, léarscáil=1, oireachtais=1, tacht=4, dhá=1, dealraitheach=4, mbeidh=3, phríomhoifigeach=1, bhunaítear=1, eagraíocht=1, meicniúil=1, réamhráiteach=2, cheann=2, horduithe=1, bliana=3, sibhialta=1, laistigh=1, ginearálta=2, bhunú=1, shuífidh=1, ndualgas=1, amháin=4, coimisinéir=2, thabhóidh=1, bheith=2, reacht=2, críocha=6, modhnú=2, dréacht=1, faoi=13, dhéanfar=1, dháil=1, féidir=1, forlíontacha=1, léiriú=1, gcomhlacht=3, féadfaidh=2, mbeartaíonn=2, mdash=7, fianaise=1, teach=3, bhfeighil=1, sórt=1, shanntar=1, riachtanach=1, stóráil=1}", result.toString());
    }

    @Test
    public void testCountWords2() throws Exception {
        String sample = "\r\n" + 
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
        "";
        Map<String, Integer> result = new WordCounter().countWords(sample);
        assertEquals(209, result.size());
        assertEquals("{tosach=1, leith=1, don=1, etc=2, leasú=3, cuid=7, search=1, rialacháin=1, mínithe=1, thoghcháin=2, achomhairc=1, dteangacha=7, taire=2, rialaithe=1, aghaidh=1, nalt=1, theanga=3, dá=2, cuardach=1, dhlíthiúla=1, fhoilsiú=1, preview=1, amach=1, leis=2, split=1, sheoladh=1, ón=1, go=3, maidir=4, oifigiúil=2, bhliantúil=1, stáiseanóireacht=1, tuarascáil=2, stáit=3, oifig=2, rialtais=1, gcríoch=1, áirithe=2, de=2, dtithe=1, iúil=1, mar=1, do=4, seirbhísí=1, feidhmeanna=2, éireann=2, marriages=1, thithe=1, c=1, a=22, cumarsáid=1, phobal=1, n=1, leanas=1, um=8, i=6, logainmneacha=3, agus=9, dhéanamh=6, r=1, sin=2, poiblí=9, en=2, thionól=1, airí=2, dara=1, bainistíocht=1, dtagraítear=1, achtaítear=1, mhíniú=1, binsí=1, tréimhsiúil=1, treoirlínte=1, bhaineann=1, scéimeanna=4, chuig=1, sc=1, bunú=1, gaolmhara=1, choimisinéirí=1, sa=2, cl=1, hachtanna=2, tuarascálacha=1, feidhm=1, foirmeacha=1, amp=1, nó=1, oireachtas=1, choimisinéara=2, ndoiciméid=1, chomhlachtaí=1, chosaint=1, gearrtheideal=1, dhaingniú=1, críoch=1, alt=1, ról=1, riaradh=2, cinn=1, oifigiúla=11, acht=8, ullmhú=1, aisghairm=1, ar=6, gcoimisinéir=2, cuanta=2, an=38, vict=1, dtaobh=1, seo=1, baile=1, ag=2, ireland=1, státseirbhíse=1, toirmeasc=1, choimisinéir=2, chun=10, oacute=1, ombudsman=2, help=1, muirear=1, fiosrúcháin=1, roinnte=1, nbsp=2, screen=1, dualgais=1, athbhreithniú=1, sholáthar=1, cheartais=2, orgain=1, úsáid=5, comhuaineach=1, linn=2, home=1, cabhair=1, eisiamh=1, imeachtaí=1, eacute=1, socrú=4, doiciméid=1, sonraí=1, teanga=2, den=1, uimhir=3, achta=2, forléiriú=1, shuirbhéireacht=2, parlaiminte=1, teangacha=1, bpobal=1, na=20, cúitimh=1, chur=2, oireachtais=4, áitiúil=1, tacht=12, dhá=1, ilghnéitheach=1, chomhaontú=1, uimh=15, gcrích=1, cha=1, orduithe=1, seirbhíse=1, riar=1, phraiticiúil=1, heorpa=2, réamhráiteach=2, caiteachais=1, leagan=1, le=6, gaeilge=2, chéad=1, pharlaimint=1, comhlacht=1, ginearálta=2, nochtadh=1, focal=1, bhunú=1, sceideal=2, coimisinéir=3, críocha=1, obair=1, cumhachtaí=1, fhionnachtana=1, nithe=1, achtanna=3, rialuithe=1, tráchtaireachtaí=1, faisnéis=2, léiriú=1, mainneachtain=1, fianaise=1, feidhme=1, in=2, print=1, ile=1, comhlachtaí=7, stát=1, ordanáis=2, amhamharc=1, dualgas=3, aacute=2, sórt=1, héireann=1, imscrúduithe=1, act=1, rúnaithe=2}", result.toString());
    }
}
