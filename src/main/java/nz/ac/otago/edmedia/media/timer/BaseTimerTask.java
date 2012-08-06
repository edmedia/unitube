package nz.ac.otago.edmedia.media.timer;

import nz.ac.otago.edmedia.spring.bean.UploadLocation;
import nz.ac.otago.edmedia.spring.service.BaseService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;
import java.util.TimerTask;

/**
 * name here.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 2/08/12
 *         Time: 9:57 AM
 */
public class BaseTimerTask extends TimerTask implements ApplicationContextAware {
    // service
    protected BaseService service;

    protected UploadLocation uploadLocation;

    protected String mailHost;

    protected String fromEmail;

    protected String smtpUsername;

    protected String smtpPassword;

    protected int smtpPort;

    protected String appURL;

    protected ApplicationContext ctx;

    // last time output log info
    private Date lastOutputLogTime;

    // output interval in hour
    private int outputIntervalInHour = 1;

    public void setService(BaseService service) {
        this.service = service;
    }

    public void setUploadLocation(UploadLocation uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ctx = applicationContext;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public void run() {
    }

    // output log?
    protected boolean outputLog() {
        boolean result;
        Date now = new Date();
        if (lastOutputLogTime == null)
            result = true;
        else
            result = DateUtils.addHours(lastOutputLogTime, outputIntervalInHour).before(now);
        if (result)
            lastOutputLogTime = now;
        return result;
    }

}
