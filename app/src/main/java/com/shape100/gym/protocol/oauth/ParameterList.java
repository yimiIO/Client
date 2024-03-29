package com.shape100.gym.protocol.oauth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ParameterList
{
  private static final char QUERY_STRING_SEPARATOR = '?';
  private static final String PARAM_SEPARATOR = "&";
  private static final String PAIR_SEPARATOR = "=";
  private static final String EMPTY_STRING = "";

  private final List<Parameter> params;
 

  public ParameterList()
  {
    params = new ArrayList<Parameter>();
  }

  ParameterList(List<Parameter> params)
  {
    this.params = new ArrayList<Parameter>(params);
  }

  public ParameterList(Map<String, String> map)
  {
    this();
    for(Map.Entry<String, String> entry : map.entrySet())
    {
      params.add(new Parameter(entry.getKey(), entry.getValue()));
    }
  }
  
  public void add(String key, String value)
  {
    params.add(new Parameter(key, value));
  }
   

  public String appendTo(String url)
  {
    String queryString = asFormUrlEncodedString();
    if (queryString.equals(EMPTY_STRING))
    {
      return url;
    }
    else
    {
      url += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
      url += queryString;
      return url;
    }
  }

  public String asOauthBaseString()
  {
	  return null;
    //return OAuthEncoder.encode(asFormUrlEncodedString());
  }

  public String asFormUrlEncodedString()
  {
    if (params.size() == 0) return EMPTY_STRING;

    StringBuilder builder = new StringBuilder();
    for(Parameter p : params)
    {
      builder.append('&').append(p.asUrlEncodedPair());
    }
    return builder.toString().substring(1);
  }
  
//  public String getPlainString(){
//	  if (params.size() == 0) return EMPTY_STRING;
//
//	    StringBuilder builder = new StringBuilder();
//	    for(Parameter p : params)
//	    {
//	      builder.append('&').append(p.asUrlEncodedPair());
//	    }
//	    return builder.toString().substring(1);
//  }

  public void addAll(ParameterList other)
  {
    params.addAll(other.params);
  }

  public void addQuerystring(String queryString)
  {
    if (queryString != null && queryString.length() > 0)
    {
      for (String param : queryString.split(PARAM_SEPARATOR))
      {
        String pair[] = param.split(PAIR_SEPARATOR);
        String key = null;//OAuthEncoder.decode(pair[0]);
        String value = null;//pair.length > 1 ? OAuthEncoder.decode(pair[1]) : EMPTY_STRING;
        params.add(new Parameter(key, value));
      }
    }
  }

  public boolean contains(Parameter param)
  {
    return params.contains(param);
  }

  public int size()
  {
    return params.size();
  }

  public ParameterList sort()
  {
    ParameterList sorted = new ParameterList(params);
    Collections.sort(sorted.params);
    return sorted;
  }
  
  public List<Parameter> getParamList(){
	  return params;
  }
}
