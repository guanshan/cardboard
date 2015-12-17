package com.wormhole.vr.cardboard;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Strings {
	public static final String NO_BROWSER_TEXT = "NO_BROWSER_TEXT";
	public static final String DIALOG_TITLE = "DIALOG_TITLE";
	public static final String CANCEL_BUTTON = "CANCEL_BUTTON";
	public static final String SETUP_BUTTON = "SETUP_BUTTON";
	public static final String DIALOG_MESSAGE_NO_CARDBOARD = "DIALOG_MESSAGE_NO_CARDBOARD";
	public static final String DIALOG_MESSAGE_SETUP = "DIALOG_MESSAGE_SETUP";
	public static final String GO_TO_PLAYSTORE_BUTTON = "GO_TO_PLAYSTORE_BUTTON";
	private static final Map<String, Map<String, String>> LANGUAGE_MAP = new HashMap();

	private static Map<String, String> getLanguageMap(Locale locale) {
		String language = locale.getLanguage();
		if (LANGUAGE_MAP.containsKey(language)) {
			return (Map) LANGUAGE_MAP.get(language);
		}

		return (Map) LANGUAGE_MAP.get("en");
	}

	private static String getString(String stringId, Locale locale) {
		Map stringMap = getLanguageMap(locale);
		if (stringMap.containsKey(stringId)) {
			return (String) stringMap.get(stringId);
		}

		return (String) ((Map) LANGUAGE_MAP.get("en")).get(stringId);
	}

	public static String getString(String stringId) {
		return getString(stringId, Locale.getDefault());
	}

	static {
		Map el = new HashMap();
		el.put("NO_BROWSER_TEXT",
				"Δεν βρέθηκε εφ. περιήγ. για άνοιγμα του ιστότοπου");
		el.put("DIALOG_TITLE", "Διαμόρφωση");
		el.put("CANCEL_BUTTON", "Ακύρωση");
		el.put("SETUP_BUTTON", "Ρύθμιση");
		el.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Αποκτήστε την εφαρμογή Cardboard για να διαμορφώσετε το σύστημα προβολής σας.");
		el.put("DIALOG_MESSAGE_SETUP",
				"Ρυθμίστε το σύστημα προβολής σας για τη βέλτιστη εμπειρία.");
		el.put("GO_TO_PLAYSTORE_BUTTON", "Μετάβαση στο Play Store");
		LANGUAGE_MAP.put("el", el);

		Map en = new HashMap();
		en.put("NO_BROWSER_TEXT", "No browser to open website.");
		en.put("DIALOG_TITLE", "Configure");
		en.put("CANCEL_BUTTON", "Cancel");
		en.put("SETUP_BUTTON", "Setup");
		en.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Get the Cardboard app in order to configure your viewer.");
		en.put("DIALOG_MESSAGE_SETUP",
				"Set up your viewer for the best experience.");
		en.put("GO_TO_PLAYSTORE_BUTTON", "Go to Play Store");
		LANGUAGE_MAP.put("en", en);

		Map vi = new HashMap();
		vi.put("NO_BROWSER_TEXT", "Không có trình duyệt nào để mở trang web");
		vi.put("DIALOG_TITLE", "Định cấu hình");
		vi.put("CANCEL_BUTTON", "Hủy");
		vi.put("SETUP_BUTTON", "Thiết lập");
		vi.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Hãy tải ứng dụng Cardboard để định cấu hình thiết bị xem của bạn.");
		vi.put("DIALOG_MESSAGE_SETUP",
				"Thiết lập thiết bị xem của bạn để có trải nghiệm tốt nhất.");
		vi.put("GO_TO_PLAYSTORE_BUTTON", "Truy cập Cửa hàng Play");
		LANGUAGE_MAP.put("vi", vi);

		Map pt_PT = new HashMap();
		pt_PT.put("NO_BROWSER_TEXT", "Sem navegador para abrir o Website");
		pt_PT.put("DIALOG_TITLE", "Configurar");
		pt_PT.put("CANCEL_BUTTON", "Cancelar");
		pt_PT.put("SETUP_BUTTON", "Configurar");
		pt_PT.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Obtenha a aplicação Cardboard para configurar o seu visualizador.");
		pt_PT.put("DIALOG_MESSAGE_SETUP",
				"Configure o seu visualizador para obter a melhor experiência.");
		pt_PT.put("GO_TO_PLAYSTORE_BUTTON", "Ir para a Play Store");
		LANGUAGE_MAP.put("pt_PT", pt_PT);

		Map it = new HashMap();
		it.put("NO_BROWSER_TEXT", "Nessun browser per l'apertura del sito web");
		it.put("DIALOG_TITLE", "Configura");
		it.put("CANCEL_BUTTON", "Annulla");
		it.put("SETUP_BUTTON", "Imposta");
		it.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Scarica l'app Cardboard per configurare il tuo visore.");
		it.put("DIALOG_MESSAGE_SETUP",
				"Imposta il visore per un'esperienza ottimale.");
		it.put("GO_TO_PLAYSTORE_BUTTON", "Visita il Play Store");
		LANGUAGE_MAP.put("it", it);

		Map iw = new HashMap();
		iw.put("NO_BROWSER_TEXT", "אין דפדפן שיכול לפתוח את האתר");
		iw.put("DIALOG_TITLE", "הגדרה");
		iw.put("CANCEL_BUTTON", "ביטול");
		iw.put("SETUP_BUTTON", "הגדר");
		iw.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"‏הורד את אפליקציית Cardboard כדי להגדיר את המציג.");
		iw.put("DIALOG_MESSAGE_SETUP",
				"הגדר את המציג לקבלת החוויה הטובה ביותר.");
		iw.put("GO_TO_PLAYSTORE_BUTTON", "‏עבור לחנות Play");
		LANGUAGE_MAP.put("iw", iw);

		Map bg = new HashMap();
		bg.put("NO_BROWSER_TEXT", "Няма браузър за отваряне на уебсайта");
		bg.put("DIALOG_TITLE", "Конфигуриране");
		bg.put("CANCEL_BUTTON", "Отказ");
		bg.put("SETUP_BUTTON", "Настройване");
		bg.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Изтеглете приложението Cardboard, за да конфигурирате очилата си.");
		bg.put("DIALOG_MESSAGE_SETUP",
				"За най-добра работа настройте очилата си.");
		bg.put("GO_TO_PLAYSTORE_BUTTON", "Към Google Play Магазин");
		LANGUAGE_MAP.put("bg", bg);

		Map es_MX = new HashMap();
		es_MX.put("NO_BROWSER_TEXT",
				"No hay ningún navegador para abrir el sitio web");
		es_MX.put("DIALOG_TITLE", "Configurar");
		es_MX.put("CANCEL_BUTTON", "Cancelar");
		es_MX.put("SETUP_BUTTON", "Configuración");
		es_MX.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Obtén la aplicación Cardboard para poder configurar el visor.");
		es_MX.put("DIALOG_MESSAGE_SETUP",
				"Configura el visor para obtener la mejor experiencia.");
		es_MX.put("GO_TO_PLAYSTORE_BUTTON", "Ir a Play Store");
		LANGUAGE_MAP.put("es_MX", es_MX);

		Map cs = new HashMap();
		cs.put("NO_BROWSER_TEXT", "Chybí prohlížeč k otevření webových stránek");
		cs.put("DIALOG_TITLE", "Konfigurovat");
		cs.put("CANCEL_BUTTON", "Zrušit");
		cs.put("SETUP_BUTTON", "Nastavit");
		cs.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Stáhněte si aplikaci Cardboard ke konfiguraci svých brýlí.");
		cs.put("DIALOG_MESSAGE_SETUP",
				"Nastavte si brýle, aby váš zážitek byl co nejlepší.");
		cs.put("GO_TO_PLAYSTORE_BUTTON", "Přejít do Obchodu Play");
		LANGUAGE_MAP.put("cs", cs);

		Map id = new HashMap();
		id.put("NO_BROWSER_TEXT", "Tidak ada browser untuk membuka situs web");
		id.put("DIALOG_TITLE", "Konfigurasikan");
		id.put("CANCEL_BUTTON", "Batal");
		id.put("SETUP_BUTTON", "Penyiapan");
		id.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Dapatkan aplikasi Cardboard untuk mengonfigurasikan penampil Anda.");
		id.put("DIALOG_MESSAGE_SETUP",
				"Siapkan penampil Anda untuk pengalaman terbaik.");
		id.put("GO_TO_PLAYSTORE_BUTTON", "Buka Play Store");
		LANGUAGE_MAP.put("id", id);

		Map es = new HashMap();
		es.put("NO_BROWSER_TEXT",
				"No tienes navegadores para abrir este sitio web");
		es.put("DIALOG_TITLE", "Configurar");
		es.put("CANCEL_BUTTON", "Cancelar");
		es.put("SETUP_BUTTON", "Configurar visor");
		es.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Descárgate la aplicación Cardboard para configurar tu visor.");
		es.put("DIALOG_MESSAGE_SETUP",
				"Configura tu visor para aprovechar todas las posibilidades de la realidad virtual.");
		es.put("GO_TO_PLAYSTORE_BUTTON", "Ir a Play Store");
		LANGUAGE_MAP.put("es", es);

		Map ru = new HashMap();
		ru.put("NO_BROWSER_TEXT", "Нет браузера для просмотра страницы");
		ru.put("DIALOG_TITLE", "Настройка");
		ru.put("CANCEL_BUTTON", "Отмена");
		ru.put("SETUP_BUTTON", "Настроить");
		ru.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Чтобы настроить очки, установите приложение Cardboard.");
		ru.put("DIALOG_MESSAGE_SETUP",
				"Для наилучших результатов настройте свои очки.");
		ru.put("GO_TO_PLAYSTORE_BUTTON", "Перейти в Play Маркет");
		LANGUAGE_MAP.put("ru", ru);

		Map nl = new HashMap();
		nl.put("NO_BROWSER_TEXT", "Geen browser om website te openen");
		nl.put("DIALOG_TITLE", "Configureren");
		nl.put("CANCEL_BUTTON", "Annuleren");
		nl.put("SETUP_BUTTON", "Instellen");
		nl.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Download de Cardboard-app om je bril te configureren.");
		nl.put("DIALOG_MESSAGE_SETUP",
				"Stel je bril in voor een optimale gebruikerservaring.");
		nl.put("GO_TO_PLAYSTORE_BUTTON", "Naar de Play Store");
		LANGUAGE_MAP.put("nl", nl);

		Map pt = new HashMap();
		pt.put("NO_BROWSER_TEXT",
				"Nenhum navegador encontrado para abrir o site");
		pt.put("DIALOG_TITLE", "Configurar");
		pt.put("CANCEL_BUTTON", "Cancelar");
		pt.put("SETUP_BUTTON", "Configurar");
		pt.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Faça o download do app do Google Cardboard para configurar seu visualizador.");
		pt.put("DIALOG_MESSAGE_SETUP",
				"Configure seu visualizador para ter a melhor experiência.");
		pt.put("GO_TO_PLAYSTORE_BUTTON", "Acessar a Google Play Store");
		LANGUAGE_MAP.put("pt", pt);

		Map nb = new HashMap();
		nb.put("NO_BROWSER_TEXT",
				"Finner ingen nettleser som kan åpne nettstedet");
		nb.put("DIALOG_TITLE", "Konfigurer");
		nb.put("CANCEL_BUTTON", "Avbryt");
		nb.put("SETUP_BUTTON", "Konfigurer");
		nb.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Skaff deg Cardboard-appen for å kunne konfigurere fremviseren din.");
		nb.put("DIALOG_MESSAGE_SETUP",
				"Konfigurer fremviseren din for en best mulig opplevelse.");
		nb.put("GO_TO_PLAYSTORE_BUTTON", "Gå til Play-butikken");
		LANGUAGE_MAP.put("nb", nb);

		Map tr = new HashMap();
		tr.put("NO_BROWSER_TEXT", "Web sitesini açacak bir tarayıcı yok");
		tr.put("DIALOG_TITLE", "Yapılandırma");
		tr.put("CANCEL_BUTTON", "İptal");
		tr.put("SETUP_BUTTON", "Kurulum");
		tr.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Gözlüğünüzü yapılandırmak için Cardboard uygulamasını indirin.");
		tr.put("DIALOG_MESSAGE_SETUP",
				"Gözlüğünüzü en iyi deneyim için hazırlayın.");
		tr.put("GO_TO_PLAYSTORE_BUTTON", "Google Play Store'a git");
		LANGUAGE_MAP.put("tr", tr);

		Map en_AU = new HashMap();
		en_AU.put("NO_BROWSER_TEXT", "No browser to open website");
		en_AU.put("DIALOG_TITLE", "Configure");
		en_AU.put("CANCEL_BUTTON", "Cancel");
		en_AU.put("SETUP_BUTTON", "Setup");
		en_AU.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Get the Cardboard app in order to configure your viewer.");
		en_AU.put("DIALOG_MESSAGE_SETUP",
				"Set up your viewer for the best experience.");
		en_AU.put("GO_TO_PLAYSTORE_BUTTON", "Go to Play Store");
		LANGUAGE_MAP.put("en_AU", en_AU);

		Map lv = new HashMap();
		lv.put("NO_BROWSER_TEXT", "Nav pārlūkprogrammas, lai atvērtu vietni");
		lv.put("DIALOG_TITLE", "Konfigurēšana");
		lv.put("CANCEL_BUTTON", "Atcelt");
		lv.put("SETUP_BUTTON", "Iestatīšana");
		lv.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Lai konfigurētu savu skatītāju, iegūstiet lietotni Cardboard.");
		lv.put("DIALOG_MESSAGE_SETUP",
				"Vislabākajiem rezultātiem iestatiet skatītāju.");
		lv.put("GO_TO_PLAYSTORE_BUTTON", "Pāriet uz Play veikalu");
		LANGUAGE_MAP.put("lv", lv);

		Map lt = new HashMap();
		lt.put("NO_BROWSER_TEXT", "Nėra naršyklės svetainei atidaryti");
		lt.put("DIALOG_TITLE", "Konfigūruoti");
		lt.put("CANCEL_BUTTON", "Atšaukti");
		lt.put("SETUP_BUTTON", "Sąranka");
		lt.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Gaukite „Cardboard“ programą, kad galėtumėte konfigūruoti žiūryklę.");
		lt.put("DIALOG_MESSAGE_SETUP",
				"Nustatykite žiūryklę, kad būtų teikiamos geriausios funkcijos.");
		lt.put("GO_TO_PLAYSTORE_BUTTON", "Eiti į „Google Play“ parduot.");
		LANGUAGE_MAP.put("lt", lt);

		Map th = new HashMap();
		th.put("NO_BROWSER_TEXT", "ไม่มีเบราว์เซอร์ที่จะใช้เปิดเว็บไซต์");
		th.put("DIALOG_TITLE", "กำหนดค่า");
		th.put("CANCEL_BUTTON", "ยกเลิก");
		th.put("SETUP_BUTTON", "ตั้งค่า");
		th.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"ดาวน์โหลดแอป Cardboard เพื่อกำหนดค่ากล่อง");
		th.put("DIALOG_MESSAGE_SETUP",
				"ตั้งค่ากล่องเพื่อรับประสบการณ์ที่ดีที่สุด");
		th.put("GO_TO_PLAYSTORE_BUTTON", "ไปที่ Play สโตร์");
		LANGUAGE_MAP.put("th", th);

		Map ro = new HashMap();
		ro.put("NO_BROWSER_TEXT", "Niciun browser pentru a deschide site-ul");
		ro.put("DIALOG_TITLE", "Configurați");
		ro.put("CANCEL_BUTTON", "Anulați");
		ro.put("SETUP_BUTTON", "Configurați");
		ro.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Descărcați aplicația Cardboard pentru a vă configura vizualizatorul.");
		ro.put("DIALOG_MESSAGE_SETUP",
				"Configurați-vă vizualizatorul pentru o utilizare optimă.");
		ro.put("GO_TO_PLAYSTORE_BUTTON", "Accesați Magazin Play");
		LANGUAGE_MAP.put("ro", ro);

		Map ar = new HashMap();
		ar.put("NO_BROWSER_TEXT", "لا يتوفَّر متصفِّح لفتح الموقع الإلكتروني");
		ar.put("DIALOG_TITLE", "التهيئة");
		ar.put("CANCEL_BUTTON", "إلغاء");
		ar.put("SETUP_BUTTON", "الإعداد");
		ar.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"‏الحصول على تطبيق Cardboard لتهيئة العارض.");
		ar.put("DIALOG_MESSAGE_SETUP", "إعداد العارض للتمتّع بأفضل تجربة.");
		ar.put("GO_TO_PLAYSTORE_BUTTON", "‏الانتقال إلى متجر Play");
		LANGUAGE_MAP.put("ar", ar);

		Map ca = new HashMap();
		ca.put("NO_BROWSER_TEXT",
				"No hi ha cap navegador per obrir el lloc web");
		ca.put("DIALOG_TITLE", "Configura");
		ca.put("CANCEL_BUTTON", "Cancel·la");
		ca.put("SETUP_BUTTON", "Configura");
		ca.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Per poder configurar el visor, baixa l'aplicació Cardboard.");
		ca.put("DIALOG_MESSAGE_SETUP",
				"Configura el visor per treure el màxim profit de Cardboard.");
		ca.put("GO_TO_PLAYSTORE_BUTTON", "Vés a Play Store");
		LANGUAGE_MAP.put("ca", ca);

		Map pl = new HashMap();
		pl.put("NO_BROWSER_TEXT",
				"Brak przeglądarki, w której można otworzyć witrynę");
		pl.put("DIALOG_TITLE", "Konfiguruj");
		pl.put("CANCEL_BUTTON", "Anuluj");
		pl.put("SETUP_BUTTON", "Konfiguracja");
		pl.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Pobierz aplikację Cardboard, aby ustawić gogle.");
		pl.put("DIALOG_MESSAGE_SETUP",
				"Ustaw gogle, aby uzyskać optymalny efekt.");
		pl.put("GO_TO_PLAYSTORE_BUTTON", "Otwórz Sklep Play");
		LANGUAGE_MAP.put("pl", pl);

		Map fr = new HashMap();
		fr.put("NO_BROWSER_TEXT", "Pas de navigateur pour ouvrir le site Web.");
		fr.put("DIALOG_TITLE", "Configurer");
		fr.put("CANCEL_BUTTON", "Annuler");
		fr.put("SETUP_BUTTON", "Configurer");
		fr.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Téléchargez l'application Cardboard afin de configurer votre visionneuse.");
		fr.put("DIALOG_MESSAGE_SETUP",
				"Pour profiter pleinement de l'application, configurez votre visionneuse.");
		fr.put("GO_TO_PLAYSTORE_BUTTON", "Accéder à Play Store");
		LANGUAGE_MAP.put("fr", fr);

		Map zh_HK = new HashMap();
		zh_HK.put("NO_BROWSER_TEXT", "沒有可開啟網站的瀏覽器");
		zh_HK.put("DIALOG_TITLE", "設定");
		zh_HK.put("CANCEL_BUTTON", "取消");
		zh_HK.put("SETUP_BUTTON", "設定");
		zh_HK.put("DIALOG_MESSAGE_NO_CARDBOARD", "取得 Cardboard 應用程式，以便設定您的檢視器。");
		zh_HK.put("DIALOG_MESSAGE_SETUP", "調整您的檢視器設定以取得最佳使用體驗。");
		zh_HK.put("GO_TO_PLAYSTORE_BUTTON", "前往 Play 商店");
		LANGUAGE_MAP.put("zh_HK", zh_HK);

		Map pt_BR = new HashMap();
		pt_BR.put("NO_BROWSER_TEXT",
				"Nenhum navegador encontrado para abrir o site");
		pt_BR.put("DIALOG_TITLE", "Configurar");
		pt_BR.put("CANCEL_BUTTON", "Cancelar");
		pt_BR.put("SETUP_BUTTON", "Configurar");
		pt_BR.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Faça o download do app do Google Cardboard para configurar seu visualizador.");
		pt_BR.put("DIALOG_MESSAGE_SETUP",
				"Configure seu visualizador para ter a melhor experiência.");
		pt_BR.put("GO_TO_PLAYSTORE_BUTTON", "Acessar a Google Play Store");
		LANGUAGE_MAP.put("pt_BR", pt_BR);

		Map hr = new HashMap();
		hr.put("NO_BROWSER_TEXT", "Nema preglednika za otvaranje web-lokacije");
		hr.put("DIALOG_TITLE", "Konfiguriraj");
		hr.put("CANCEL_BUTTON", "Odustani");
		hr.put("SETUP_BUTTON", "Postavljanje");
		hr.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Nabavite aplikaciju Cardboard da biste konfigurirali masku za virtualnu stvarnost.");
		hr.put("DIALOG_MESSAGE_SETUP",
				"Postavite masku za virtualnu stvarnost da biste u potpunosti iskoristili sve značajke.");
		hr.put("GO_TO_PLAYSTORE_BUTTON", "Idi na Trgovinu Play");
		LANGUAGE_MAP.put("hr", hr);

		Map es_US = new HashMap();
		es_US.put("NO_BROWSER_TEXT",
				"No hay ningún navegador para abrir el sitio web");
		es_US.put("DIALOG_TITLE", "Configurar");
		es_US.put("CANCEL_BUTTON", "Cancelar");
		es_US.put("SETUP_BUTTON", "Configuración");
		es_US.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Obtén la aplicación Cardboard para poder configurar el visor.");
		es_US.put("DIALOG_MESSAGE_SETUP",
				"Configura el visor para obtener la mejor experiencia.");
		es_US.put("GO_TO_PLAYSTORE_BUTTON", "Ir a Play Store");
		LANGUAGE_MAP.put("es_US", es_US);

		Map zh_TW = new HashMap();
		zh_TW.put("NO_BROWSER_TEXT", "沒有可開啟網站的瀏覽器");
		zh_TW.put("DIALOG_TITLE", "設定");
		zh_TW.put("CANCEL_BUTTON", "取消");
		zh_TW.put("SETUP_BUTTON", "設定");
		zh_TW.put("DIALOG_MESSAGE_NO_CARDBOARD", "取得 Cardboard 應用程式，以便設定您的檢視器。");
		zh_TW.put("DIALOG_MESSAGE_SETUP", "調整您的檢視器設定以取得最佳使用體驗。");
		zh_TW.put("GO_TO_PLAYSTORE_BUTTON", "前往 Play 商店");
		LANGUAGE_MAP.put("zh_TW", zh_TW);

		Map hu = new HashMap();
		hu.put("NO_BROWSER_TEXT",
				"Nem található böngésző a webhely megnyitásához");
		hu.put("DIALOG_TITLE", "Konfigurálás");
		hu.put("CANCEL_BUTTON", "Mégse");
		hu.put("SETUP_BUTTON", "Beállítás");
		hu.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Szemüvege konfigurálásához töltse le a Cardboard alkalmazást.");
		hu.put("DIALOG_MESSAGE_SETUP",
				"A szemüveg beállítása a legjobb élmény eléréséhez.");
		hu.put("GO_TO_PLAYSTORE_BUTTON", "Play Áruház megnyitása");
		LANGUAGE_MAP.put("hu", hu);

		Map fa = new HashMap();
		fa.put("NO_BROWSER_TEXT", "مرورگری برای باز کردن وب‌سایت نیست");
		fa.put("DIALOG_TITLE", "پیکربندی");
		fa.put("CANCEL_BUTTON", "لغو");
		fa.put("SETUP_BUTTON", "راه‌اندازی");
		fa.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"‏برای پیکربندی نظاره‌گر، برنامه Cardboard را دریافت کنید.");
		fa.put("DIALOG_MESSAGE_SETUP",
				"برای اینکه بهترین تجربه را داشته باشید، نظاره‌گرتان را راه‌اندازی کنید.");
		fa.put("GO_TO_PLAYSTORE_BUTTON", "‏برو به فروشگاه Play");
		LANGUAGE_MAP.put("fa", fa);

		Map hi = new HashMap();
		hi.put("NO_BROWSER_TEXT", "वेबसाइट खोलने के लिए कोई ब्राउज़र नहीं");
		hi.put("DIALOG_TITLE", "कॉन्फ़िगर करें");
		hi.put("CANCEL_BUTTON", "रोकें");
		hi.put("SETUP_BUTTON", "सेटअप");
		hi.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"अपना व्यूअर कॉन्फ़िगर करने के लिए कार्डबोर्ड ऐप्लिकेशन प्राप्त करें.");
		hi.put("DIALOG_MESSAGE_SETUP",
				"श्रेष्ठ अनुभव के लिए अपना व्यूअर सेट करें.");
		hi.put("GO_TO_PLAYSTORE_BUTTON", "Play स्‍टोर पर जाएं");
		LANGUAGE_MAP.put("hi", hi);

		Map fi = new HashMap();
		fi.put("NO_BROWSER_TEXT",
				"Ei verkkosivuston avaamiseen sopivaa selainta");
		fi.put("DIALOG_TITLE", "Määritä");
		fi.put("CANCEL_BUTTON", "Peruuta");
		fi.put("SETUP_BUTTON", "Aloita määritys");
		fi.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Lataa Cardboard-sovellus lasien asetusten määrittämistä varten.");
		fi.put("DIALOG_MESSAGE_SETUP",
				"Määritä lasien asetukset, jotta katselukokemus on mahdollisimman miellyttävä.");
		fi.put("GO_TO_PLAYSTORE_BUTTON", "Siirry Play Kauppaan");
		LANGUAGE_MAP.put("fi", fi);

		Map da = new HashMap();
		da.put("NO_BROWSER_TEXT", "Der er ingen browser til at åbne websitet");
		da.put("DIALOG_TITLE", "Konfigurer");
		da.put("CANCEL_BUTTON", "Annuller");
		da.put("SETUP_BUTTON", "Konfigurer");
		da.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Få Cardboard-appen, så du kan konfigurere fremviseren.");
		da.put("DIALOG_MESSAGE_SETUP",
				"Konfigurer din fremviser for at få den bedste oplevelse.");
		da.put("GO_TO_PLAYSTORE_BUTTON", "Gå til Play Butik");
		LANGUAGE_MAP.put("da", da);

		Map en_IN = new HashMap();
		en_IN.put("NO_BROWSER_TEXT", "No browser to open website");
		en_IN.put("DIALOG_TITLE", "Configure");
		en_IN.put("CANCEL_BUTTON", "Cancel");
		en_IN.put("SETUP_BUTTON", "Setup");
		en_IN.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Get the Cardboard app in order to configure your viewer.");
		en_IN.put("DIALOG_MESSAGE_SETUP",
				"Set up your viewer for the best experience.");
		en_IN.put("GO_TO_PLAYSTORE_BUTTON", "Go to Play Store");
		LANGUAGE_MAP.put("en_IN", en_IN);

		Map ja = new HashMap();
		ja.put("NO_BROWSER_TEXT", "ウェブサイトを開くブラウザがありません");
		ja.put("DIALOG_TITLE", "設定");
		ja.put("CANCEL_BUTTON", "キャンセル");
		ja.put("SETUP_BUTTON", "セットアップ");
		ja.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"ビューアを設定するには、Cardboardアプリを入手してください。");
		ja.put("DIALOG_MESSAGE_SETUP", "快適にご利用いただくために、ビューアをセットアップしてください。");
		ja.put("GO_TO_PLAYSTORE_BUTTON", "Google Playストアへ");
		LANGUAGE_MAP.put("ja", ja);

		Map he = new HashMap();
		he.put("NO_BROWSER_TEXT", "אין דפדפן שיכול לפתוח את האתר");
		he.put("DIALOG_TITLE", "הגדרה");
		he.put("CANCEL_BUTTON", "ביטול");
		he.put("SETUP_BUTTON", "הגדר");
		he.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"‏הורד את אפליקציית Cardboard כדי להגדיר את המציג.");
		he.put("DIALOG_MESSAGE_SETUP",
				"הגדר את המציג לקבלת החוויה הטובה ביותר.");
		he.put("GO_TO_PLAYSTORE_BUTTON", "‏עבור לחנות Play");
		LANGUAGE_MAP.put("he", he);

		Map zh_CN = new HashMap();
		zh_CN.put("NO_BROWSER_TEXT", "找不到可以打开网站的浏览器");
		zh_CN.put("DIALOG_TITLE", "配置");
		zh_CN.put("CANCEL_BUTTON", "取消");
		zh_CN.put("SETUP_BUTTON", "设置");
		zh_CN.put("DIALOG_MESSAGE_NO_CARDBOARD", "获取 Cardboard 应用以配置您的眼镜。");
		zh_CN.put("DIALOG_MESSAGE_SETUP", "设置眼镜以获得最佳体验。");
		zh_CN.put("GO_TO_PLAYSTORE_BUTTON", "转到 Play 商店");
		LANGUAGE_MAP.put("zh_CN", zh_CN);

		Map sr = new HashMap();
		sr.put("NO_BROWSER_TEXT", "Нема прегледача за отварање веб-сајта");
		sr.put("DIALOG_TITLE", "Конфигуришите");
		sr.put("CANCEL_BUTTON", "Откажи");
		sr.put("SETUP_BUTTON", "Подешавање");
		sr.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Преузмите апликацију Cardboard да бисте конфигурисали маску.");
		sr.put("DIALOG_MESSAGE_SETUP", "Подесите маску за најбољи доживљај.");
		sr.put("GO_TO_PLAYSTORE_BUTTON", "Иди у Play продавницу");
		LANGUAGE_MAP.put("sr", sr);

		Map ko = new HashMap();
		ko.put("NO_BROWSER_TEXT", "웹사이트를 열 브라우저가 없습니다.");
		ko.put("DIALOG_TITLE", "설정");
		ko.put("CANCEL_BUTTON", "취소");
		ko.put("SETUP_BUTTON", "설정");
		ko.put("DIALOG_MESSAGE_NO_CARDBOARD", "뷰어를 설정하려면 Cardboard 앱을 받으세요.");
		ko.put("DIALOG_MESSAGE_SETUP", "최적의 경험을 위해 뷰어를 설정하세요.");
		ko.put("GO_TO_PLAYSTORE_BUTTON", "Play 스토어로 이동");
		LANGUAGE_MAP.put("ko", ko);

		Map sv = new HashMap();
		sv.put("NO_BROWSER_TEXT", "Ingen webbläsare kan öppna webbsidan");
		sv.put("DIALOG_TITLE", "Konfigurera");
		sv.put("CANCEL_BUTTON", "Avbryt");
		sv.put("SETUP_BUTTON", "Konfiguration");
		sv.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Ladda ned Cardboard-appen om du vill konfigurera visaren.");
		sv.put("DIALOG_MESSAGE_SETUP",
				"Konfigurera visaren för bästa upplevelse.");
		sv.put("GO_TO_PLAYSTORE_BUTTON", "Öppna Play Butik");
		LANGUAGE_MAP.put("sv", sv);

		Map sk = new HashMap();
		sk.put("NO_BROWSER_TEXT", "Žiaden prehliadač na otvorenie webu");
		sk.put("DIALOG_TITLE", "Konfigurácia");
		sk.put("CANCEL_BUTTON", "Zrušiť");
		sk.put("SETUP_BUTTON", "Nastaviť");
		sk.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Získajte aplikáciu Cardboard, aby ste mohli konfigurovať zobrazovač.");
		sk.put("DIALOG_MESSAGE_SETUP",
				"Nastavte si zobrazovač a dosiahnite tie najlepšie výsledky.");
		sk.put("GO_TO_PLAYSTORE_BUTTON", "Prejsť do služby Obchod Play");
		LANGUAGE_MAP.put("sk", sk);

		Map de = new HashMap();
		de.put("NO_BROWSER_TEXT",
				"Kein Browser zum Öffnen der Website gefunden");
		de.put("DIALOG_TITLE", "Konfigurieren");
		de.put("CANCEL_BUTTON", "Abbrechen");
		de.put("SETUP_BUTTON", "Einrichtung");
		de.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Holen Sie sich die Cardboard App zum Konfigurieren Ihrer VR-Brille.");
		de.put("DIALOG_MESSAGE_SETUP",
				"Richten Sie Ihre VR-Brille für optimale Virtual-Reality-Erlebnisse ein.");
		de.put("GO_TO_PLAYSTORE_BUTTON", "Zum Play Store");
		LANGUAGE_MAP.put("de", de);

		Map en_GB = new HashMap();
		en_GB.put("NO_BROWSER_TEXT", "No browser to open website");
		en_GB.put("DIALOG_TITLE", "Configure");
		en_GB.put("CANCEL_BUTTON", "Cancel");
		en_GB.put("SETUP_BUTTON", "Setup");
		en_GB.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Get the Cardboard app in order to configure your viewer.");
		en_GB.put("DIALOG_MESSAGE_SETUP",
				"Set up your viewer for the best experience.");
		en_GB.put("GO_TO_PLAYSTORE_BUTTON", "Go to Play Store");
		LANGUAGE_MAP.put("en_GB", en_GB);

		Map fil = new HashMap();
		fil.put("NO_BROWSER_TEXT", "Walang browser upang buksan ang website");
		fil.put("DIALOG_TITLE", "I-configure");
		fil.put("CANCEL_BUTTON", "Kanselahin");
		fil.put("SETUP_BUTTON", "I-setup");
		fil.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Kunin ang Cardboard app upang i-configure ang iyong viewer.");
		fil.put("DIALOG_MESSAGE_SETUP",
				"I-set up ang iyong viewer para sa pinakamagandang karanasan.");
		fil.put("GO_TO_PLAYSTORE_BUTTON", "Pumunta sa Play Store");
		LANGUAGE_MAP.put("fil", fil);

		Map uk = new HashMap();
		uk.put("NO_BROWSER_TEXT", "Немає веб-переглядача для сайту");
		uk.put("DIALOG_TITLE", "Налаштувати");
		uk.put("CANCEL_BUTTON", "Скасувати");
		uk.put("SETUP_BUTTON", "Налаштування");
		uk.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Завантажте додаток Cardboard, щоб налаштувати окуляри.");
		uk.put("DIALOG_MESSAGE_SETUP",
				"Налаштуйте окуляри й отримайте найкращі враження.");
		uk.put("GO_TO_PLAYSTORE_BUTTON", "Перейти в Google Play");
		LANGUAGE_MAP.put("uk", uk);

		Map sl = new HashMap();
		sl.put("NO_BROWSER_TEXT",
				"Tega mesta ni mogoče odpreti z nobenim brskalnikom");
		sl.put("DIALOG_TITLE", "Konfiguriranje");
		sl.put("CANCEL_BUTTON", "Prekliči");
		sl.put("SETUP_BUTTON", "Namesti");
		sl.put("DIALOG_MESSAGE_NO_CARDBOARD",
				"Če želite konfigurirati pregledovalnik, namestite aplikacijo Cardboard.");
		sl.put("DIALOG_MESSAGE_SETUP",
				"Da bo izkušnja čim boljša, namestite pregledovalnik.");
		sl.put("GO_TO_PLAYSTORE_BUTTON", "V Google Play");
		LANGUAGE_MAP.put("sl", sl);
	}
}