package org.cloudfoundry.identity.uaa.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UaaUserTests {
    @Test
    public void emailFromShouldReturnNamePlusDefaultDomainWhenUsernameDoesNotContainAtSymbol() {
        assertEquals(String.format("name@%s", UaaUser.DEFAULT_EMAIL_DOMAIN), UaaUser.emailFrom("name"));
    }

    @Test
    public void emailFromShouldReturnNamePlusDefaultDomainWhenUsernameContainsLeadingAtSymbol() {
        assertEquals(String.format("name@%s", UaaUser.DEFAULT_EMAIL_DOMAIN), UaaUser.emailFrom("@name"));
    }

    @Test
    public void emailFromShouldReturnNamePlusDefaultDomainWhenUsernameContainsTrailingAtSymbol() {
        assertEquals(String.format("name@%s", UaaUser.DEFAULT_EMAIL_DOMAIN), UaaUser.emailFrom("name@"));
    }

    @Test
    public void emailFromShouldReturnUsernameWhenUsernameLooksLikeAnEmailAddress() {
        assertEquals("name@example.com", UaaUser.emailFrom("name@example.com"));
    }

    @Test
    public void withIncompletePrototypeShouldDefaultEmailFromUsernameWhenMissing() {
        UaaUserPrototype prototype = new UaaUserPrototype().withUsername("name");
        UaaUser user = UaaUser.fromIncompletePrototype(prototype);
        assertEquals(String.format("name@%s", UaaUser.DEFAULT_EMAIL_DOMAIN), user.getEmail());
    }

    @Test
    public void withIncompletePrototypeShouldDefaultGivenNameFromEmailWhenMissing() {
        UaaUserPrototype prototype = new UaaUserPrototype().withUsername("name").withEmail("name@example.com");
        UaaUser user = UaaUser.fromIncompletePrototype(prototype);
        assertEquals("name", user.getGivenName());
    }

    @Test
    public void withIncompletePrototypeShouldDefaultFamilyNameFromEmailWhenMissing() {
        UaaUserPrototype prototype = new UaaUserPrototype().withUsername("name").withEmail("name@example.com");
        UaaUser user = UaaUser.fromIncompletePrototype(prototype);
        assertEquals("example.com", user.getFamilyName());
    }

    @Test
    public void withIncompletePrototypeShouldDefaultFamilyNameFromInvalidEmailWhenMissing() {
        UaaUserPrototype prototype = new UaaUserPrototype().withUsername("name").withEmail("name");
        UaaUser user = UaaUser.fromIncompletePrototype(prototype);
        assertEquals("name", user.getFamilyName());
    }
}