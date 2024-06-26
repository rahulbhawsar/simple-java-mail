package org.simplejavamail.converter.internal.mimemessage;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.email.internal.InternalEmail;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Finds a compatible {@link SpecializedMimeMessageProducer} for a given Email and produces a MimeMessage accordingly.
 * <p>
 * This way, a MimeMessage structure will always be as succinct as possible, so that email clients will never get confused due to missing parts (such
 * as no attachments in a "mixed" multipart or no embedded images in a "related" multipart).
 * <p>
 * Also see issue <a href="https://github.com/bbottema/simple-java-mail/issues/144">#144</a>
 */
public final class MimeMessageProducerHelper {
	
	private static final List<SpecializedMimeMessageProducer> mimeMessageProducers = Arrays.asList(
			new MimeMessageProducerSimple(),
			new MimeMessageProducerAlternative(),
			new MimeMessageProducerRelated(),
			new MimeMessageProducerMixed(),
			new MimeMessageProducerMixedRelated(),
			new MimeMessageProducerMixedAlternative(),
			new MimeMessageProducerRelatedAlternative(),
			new MimeMessageProducerMixedRelatedAlternative()
	);
	
	private MimeMessageProducerHelper() {
	}
	
	@SuppressWarnings("deprecation")
	public static MimeMessage produceMimeMessage(Email email, Session session) throws UnsupportedEncodingException, MessagingException {
		assert email instanceof InternalEmail;
		((InternalEmail) email).verifyDefaultsAndOverridesApplied();
		for (SpecializedMimeMessageProducer mimeMessageProducer : mimeMessageProducers) {
			if (mimeMessageProducer.compatibleWithEmail(email)) {
				return mimeMessageProducer.populateMimeMessage(email, session);
			}
		}
		throw new IllegalStateException("no compatible SpecializedMimeMessageProducer found for email");
	}
}