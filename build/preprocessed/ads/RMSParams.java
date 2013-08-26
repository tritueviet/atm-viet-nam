package ads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.rms.RecordStore;

public class RMSParams
{
	private String RS_NAME = "Parameters";
	
	private Hashtable m_cache;
	
	public RMSParams()
	{
		m_cache = new Hashtable();
		init();
	}
	
	public void init()
	{
		RecordStore rs = null;
		try {
		rs = RecordStore.openRecordStore(RS_NAME, true);
		if (rs.getNumRecords() > 0)
		{
			load(rs.getRecord(1),0);
		}
		else
		{
			byte[] buffer = new byte[4];
			rs.addRecord(buffer, 0, buffer.length);
		}
		}
		catch (Exception e)
		{ 
			System.out.println(e);
		}
		finally {
			if (rs != null)
				try
				{
					rs.closeRecordStore();
				} catch (Exception e)
				{}
		}
	}
	
	private void load(byte[] buffer,int offset)
	{
		m_cache.clear();
		RecordStore rs = null;
		try {
			rs = RecordStore.openRecordStore(RS_NAME, false);
			byte[] temp = rs.getRecord(1);
			DataInputStream din = new DataInputStream(new ByteArrayInputStream(temp));
			int numVals = din.readInt();
			for (int i=0;i<numVals;++i)
			{
				String key = din.readUTF();
				String val = din.readUTF();
				m_cache.put(key, val);
			}
		}
		catch (Exception e)
		{ 
			System.out.println(e);
		}
		finally {
			if (rs != null)
				try
				{
					rs.closeRecordStore();
				} catch (Exception e)
				{}
		}
	}
	
	public void put(String key, String value, boolean save)
	{ 
		m_cache.put(key, value);
		if (save)
			save();
	}
	
	public void put(String key, String value)
	{ 
		put(key,value,true);
	}
	
	public String get(String key)
	{
		return get(key, null);
	}
	
	public String get(String key, String def)
	{
		String val = (String) m_cache.get(key);
		if (val == null) return def;
		return val;
	}
	
	public void save()
	{
		RecordStore rs = null;
		try {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeInt(m_cache.size());
		Enumeration en = m_cache.keys();
		while (en.hasMoreElements())
		{
			String key = (String) en.nextElement();
			String val = (String) m_cache.get(key);
			dos.writeUTF(key);
			dos.writeUTF(val);
		}
		rs = RecordStore.openRecordStore(RS_NAME, false);
		byte[] temp = bos.toByteArray();
		rs.setRecord(1, temp, 0, temp.length);
		
		}
		catch (Exception e)
		{ 
			System.out.println(e);
		}
		finally {
			if (rs != null)
				try
				{
					rs.closeRecordStore();
				} catch (Exception e)
				{}
		}
	}
}
