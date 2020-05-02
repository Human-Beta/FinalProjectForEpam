package ua.nure.shishov.finaltask.jsp.customtag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mysql.cj.util.StringUtils;

public class RedirectWithCommandAndParams extends SimpleTagSupport implements DynamicAttributes {
	private String commandName;
	private Map<String, String> params = new HashMap<>();

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	

	@Override
	public void doTag() throws JspException, IOException {
		PageContext context = (PageContext) getJspContext();
		HttpServletResponse resp = (HttpServletResponse) context.getResponse();
		StringBuilder path = new StringBuilder()
				.append("controller?command=").append(commandName).append("&");
		
		for (Map.Entry<String, String> pair : params.entrySet()) {
			path.append(pair.getKey()).append("=").append(pair.getValue()).append("&");
		}
		path.delete(path.length() - 1, path.length());
		
		resp.sendRedirect(path.toString());
	}
	

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if (value == null) {
			return;
		}

		if (value instanceof String) {
			String valueStr = (String) value;
			if (StringUtils.isNullOrEmpty(valueStr)) {
				throw new IllegalArgumentException("Attribute`s value is null.");
			}

			params.put(localName, valueStr);
		} else {
			throw new IllegalArgumentException("Attribute`s value is not a string.");
		}
	}

}
