package nz.ac.otago.edmedia.media.controller;

import nz.ac.otago.edmedia.spring.controller.BaseOperationController;

/**
 * Email controller, for controllers need to send out email.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 29/02/12
 *         Time: 9:59 AM
 */
public abstract class EmailController extends BaseOperationController {

    private String mailHost;

    private String fromEmail;

    private String smtpUsername;

    private String smtpPassword;

    private int smtpPort;

    public String getMailHost() {
        return mailHost;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

}
