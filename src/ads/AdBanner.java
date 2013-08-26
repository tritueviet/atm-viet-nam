package ads;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.sun.lwuit.Component;
import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.plaf.Style;
import com.sun.lwuit.xml.Element;
import com.sun.lwuit.xml.XMLParser;

/**
 * @author ifrach shai
 * 
 */
public class AdBanner extends Label {

	private static final String AD_URL = "http://m2m1.inner-active.com/simpleM2M/clientRequestAd";
	private static final String AD_VERSION = "Sm2m-1.5.3";
	private static final int AD_PO = 551;

	private String adID;
	private String imei;
	private String cid;
	private String ua;
	private String age;
	private String gender;
	private String location;

	// -1 value for refreshRate means disabled all other values are in seconds
	private int refreshRate;

	private long lastUpdatedTime;

	private RMSParams rms;

	private String link;
	private String bannerURL;
	private String adText;

	/**
	 * 
	 */
	public AdBanner()
	{
		lastUpdatedTime = 0;
		refreshRate = -1;
		imei = null;
		imei = System.getProperty("com.nokia.mid.imei");
		rms = new RMSParams();
		cid = rms.get("cid", null);
		adID = "";
		age = "";
		gender = "";
		location = "";
		ua = System.getProperty("microedition.platform");
		if (ua != null)
			ua = urlEncode(ua);
		setFocusable(true);
		setTextPosition(Component.TOP);
		setGap(0);
		Style s = getUnselectedStyle();
		s.setPadding(0, 0, 0, 0);
		s.setMargin(0, 0, 0, 0);
		s.setBgTransparency(0);
		s.setAlignment(Component.CENTER);
		setUnselectedStyle(s);
		setSelectedStyle(s);
		setPressedStyle(s);
		setDisabledStyle(s);

	}

	/**
	 * @param adID - the ad identifier in InnerActive
	 */
	public AdBanner(String adID)
	{
		this(adID, -1);
	}

	/**
	 * @param adID - the ad identifier in InnerActive
	 * @param refreshRate - refresh rate for the ad (-1 for no refresh)
	 */
	public AdBanner(String adID, int refreshRate)
	{
		this();
		this.adID = adID;
		this.refreshRate = refreshRate;
	}

	public void keyReleased(int code)
	{
		if (Display.getInstance().getGameAction(code) == Display.GAME_FIRE)
		{
			adClicked();
		}
	}

	public void pointerReleased(int x, int y)
	{
		if (!isDragActivated())
		{
			adClicked();
		}
	}

	private void adClicked()
	{
		if (link != null && link != "")
		{
			Display.getInstance().execute(link);
		}
	}

	private void loadAd()
	{
		String url = AD_URL + "?aid=" + adID + "&v=" + AD_VERSION + "&po=" + AD_PO;
		url += "&w=" + Display.getInstance().getDisplayWidth() + "&h=" + Display.getInstance().getDisplayWidth();
		if (ua != null)
			url += "&ua=" + ua;
		if (cid != null)
		{
			url += "&cid=" + cid;
		}
		if (imei != null)
		{
			url += "&hid=" + imei;
		}
		if (age != "")
		{
			url += "&a=" + age;
		}
		if (gender != "")
		{
			url += "&g=" + gender;
		}
		if (location != "")
		{
			url += "&l=" + location;
		}
		HttpConnection con = null;
		InputStream is = null;
		bannerURL = "";
		try
		{
			con = (HttpConnection) Connector.open(url);
			con.setRequestMethod(HttpConnection.GET);
			if (con.getResponseCode() != HttpConnection.HTTP_OK)
				return;
			XMLParser xml = new XMLParser();
			Element node = xml.parse(new InputStreamReader(is = con.openInputStream(), "UTF-8"));
			String responseError = node.getAttribute("error").trim().toLowerCase();
			if (!responseError.equals("ok"))
				return;
			for (int i = 0; i < node.getNumChildren(); i++)
			{
				Element n = node.getChildAt(i);
				String tagName = n.getTagName().trim().toLowerCase();
				if (tagName.equals("tns:client"))
				{
					rms.put("cid", n.getAttribute("id").trim());
					rms.save();
				} else if (tagName.equals("tns:ad"))
				{
					for (int j = 0; j < n.getNumChildren(); j++)
					{
						Element adNode = n.getChildAt(j);
						String adTagName = adNode.getTagName().trim().toLowerCase();
						if (adTagName.equals("tns:text"))
						{
							if (adNode.getNumChildren() > 0)
								adText = adNode.getChildAt(0).getText().trim();
						}
						if (adTagName.equals("tns:image"))
						{
							if (adNode.getNumChildren() > 0)
								bannerURL = adNode.getChildAt(0).getText().trim();
						}
						if (adTagName.equals("tns:url"))
						{
							link = adNode.getChildAt(0).getText().trim();
						}
					}
					if (bannerURL == "")
					{
						setText(adText);
						setIcon(null);
					} else
					{
						new Thread() {
							public void run()
							{
								try
								{
									Thread.sleep(100);
								} catch (InterruptedException e)
								{
								}
								HttpConnection con = null;
								InputStream is = null;
								try
								{
									con = (HttpConnection) Connector.open(bannerURL);
									con.setRequestMethod(HttpConnection.GET);
									if (con.getResponseCode() != HttpConnection.HTTP_OK)
										return;
									int read = -1;
									ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
									byte[] buffer = new byte[10240];
									is = con.openInputStream();
									while ((read = is.read(buffer)) > 0)
										bos.write(buffer, 0, read);
                                                                        Resize re= new Resize();
									setIcon(re.resizeImage(Image.createImage(bos.toByteArray(), 0, bos.size()), 30, 240));
									setText("");
									setShouldCalcPreferredSize(true);
									if (getParent() != null)
										getParent().revalidate();
								} catch (Exception e)
								{
								} finally
								{
									if (is != null)
										try
									{
											is.close();
									} catch (Exception e)
									{
									}
									if (con != null)
										try
									{
											con.close();
									} catch (Exception e)
									{
									}
								}
							}
						}.start();

					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		} finally
		{
			if (is != null)
				try
			{
					is.close();
			} catch (Exception e)
			{
			}
			if (con != null)
				try
			{
					con.close();
			} catch (Exception e)
			{
			}
		}
	}

	public void initComponent()
	{
		if (refreshRate == -1)
		{
			loadAd();
		} else
		{
			getComponentForm().registerAnimated(this);
		}
	}

	public boolean animate()
	{
		Form f = getComponentForm();
		if (f == null || !f.isVisible())
		{
			return false;
		}
		if (System.currentTimeMillis() - lastUpdatedTime > refreshRate * 1000)
		{
			lastUpdatedTime = System.currentTimeMillis();
			loadAd();
		}
		return super.animate();
	}

	/**
	 * @return the adID
	 */
	public String getAdID()
	{
		return adID;
	}

	/**
	 * @param adID the adID to set
	 */
	public void setAdID(String adID)
	{
		this.adID = adID;
	}

	/**
	 * @return the age
	 */
	public String getAge()
	{
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age)
	{
		this.age = age;
	}

	/**
	 * @return the gender
	 */
	public String getGender()
	{
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}

	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	public static String urlEncode(String str)
	{
		StringBuffer sb = new StringBuffer();
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; ++i)
		{
			sb.append("%" + Integer.toHexString(chars[i]));
		}
		return sb.toString();
	}
}
