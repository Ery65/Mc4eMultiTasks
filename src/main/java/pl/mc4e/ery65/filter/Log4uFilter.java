package pl.mc4e.ery65.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class Log4uFilter implements Filter {
	
	public Log4uFilter(){
	}
	
	public Result filter(LogEvent e) {
		if (e.getMessage()==null||e==null){
			return Result.NEUTRAL;
		} else {
			if (e.getMessage().toString().contains("issued server command:")){
				System.out.println("1");
				return Result.DENY;
			} else
				return Result.ACCEPT;
		}
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3,
			Object... arg4) {
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
