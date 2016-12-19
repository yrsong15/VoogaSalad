package viewformatter_util.exception;

import viewformatter_util.formatobjects.ViewObject;

public class ViewFormatterException extends Exception
{
	public ViewFormatterException(ViewObject viewObject)
	{
		super(viewObject.getViewObjectID() + "\" does not have a representative Node.");
	}
}
