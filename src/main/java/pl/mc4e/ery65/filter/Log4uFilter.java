package pl.mc4e.ery65.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class Log4uFilter implements Filter {
	
	
	public Result filter(LogEvent e) {
		if (e.getMessage()==null||e==null){
			return Result.ACCEPT;
		} else {
			String msg = e.getMessage().toString();
			if (msg.contains("/l")|| msg.contains("/log") || msg.contains("/reg")||msg.contains("/rej")
					||msg.contains("/rejestruj")||msg.contains("/register")||msg.contains("/l")||msg.contains("/login")){
				return Result.DENY;
			} else
				return Result.ACCEPT;
		}
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3,
			Object... arg4) {
		String msg = arg3;
		if (msg !=null){
			if (msg.contains("/l")|| msg.contains("/log") || msg.contains("/reg")||msg.contains("/rej")
					||msg.contains("/rejestruj")||msg.contains("/register")||msg.contains("/l")||msg.contains("/login")){
				return Result.DENY;
			} else
				return Result.ACCEPT;
		}
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, Object arg3,
			Throwable arg4) {
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, Message arg3,
			Throwable arg4) {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMismatch() {
		return Result.NEUTRAL;
	}

}
