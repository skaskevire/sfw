package com.sfw.resource;

import java.io.IOException;
import java.io.InputStream;

public interface InternalSystemResource
{
	String sendDataToInternalSystem( InputStream is) throws IOException;
}
