package dao;

import java.io.*;

public class HeaderlessObjectOutputStream extends ObjectOutputStream {

public HeaderlessObjectOutputStream(OutputStream out)throws IOException
{
super(out);

}
@Override
protected void writeStreamHeader() throws IOException
{

}
}
