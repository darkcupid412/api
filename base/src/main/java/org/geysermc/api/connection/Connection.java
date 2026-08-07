/*
 * Copyright (c) 2019-2026 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.api.connection;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.geysermc.api.util.BedrockPlatform;
import org.geysermc.api.util.InputMode;
import org.geysermc.api.util.UiProfile;
import org.geysermc.cumulus.datadrivenui.DataDrivenCapabilities;
import org.geysermc.cumulus.datadrivenui.DataDrivenForm;
import org.geysermc.cumulus.datadrivenui.DataDrivenMessageBox;
import org.geysermc.cumulus.datadrivenui.DataDrivenSession;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a player connection.
 */
public interface Connection {
    /**
     * Returns the bedrock name of the connection.
     */
    String bedrockUsername();

    /**
     * Returns the java name of the connection.
     */
    @MonotonicNonNull
    String javaUsername();

    /**
     * Returns the UUID of the connection.
     */
    @MonotonicNonNull
    UUID javaUuid();

    /**
     * Returns the XUID of the connection.
     */
    String xuid();

    /**
     * Returns the version of the Bedrock client.
     */
    String version();

    /**
     * Returns the platform that the connection is playing on.
     */
    BedrockPlatform platform();

    /**
     * Returns the language code of the connection.
     */
    String languageCode();

    /**
     * Returns the User Interface Profile of the connection.
     */
    UiProfile uiProfile();

    /**
     * Returns the Input Mode of the Bedrock client.
     */
    InputMode inputMode();

    /**
     * Returns whether the connection is linked.
     * This will always return false when the auth-type isn't Floodgate.
     */
    boolean isLinked();

    /**
     * Sends a form to the connection and opens it.
     *
     * @param form the form to send
     * @return whether the form was successfully sent
     */
    boolean sendForm(Form form);

    /**
     * Sends a form to the connection and opens it.
     *
     * @param formBuilder the formBuilder to send
     * @return whether the form was successfully sent
     */
    boolean sendForm(FormBuilder<?, ?, ?> formBuilder);

    /**
     * Returns what this connection can do with data-driven forms. Check this before building one;
     * when the state isn't {@link DataDrivenCapabilities.State#SUPPORTED}, fall back to a regular
     * {@link Form}.
     *
     * @return what this connection can do with data-driven forms
     * @since 1.1.0
     */
    default DataDrivenCapabilities dataDrivenCapabilities() {
        return DataDrivenCapabilities.unsupported();
    }

    /**
     * Shows a data-driven form on the connection.
     *
     * <p>The form stays live while it is open: its buttons run their callbacks without closing it,
     * the player's edits arrive through its observables, and setting those observables changes what
     * the player sees. How it ends is reported to the form's close callback; the returned session
     * only closes it early.
     *
     * @param form the form to show; a form is built for one showing
     * @return a handle to the showing, or null when this connection doesn't support data-driven
     *         forms, which {@link #dataDrivenCapabilities()} says in advance
     * @throws IllegalStateException when the form has already been shown
     * @throws IllegalArgumentException when the form holds a component this connection cannot
     *         show, which {@link #dataDrivenCapabilities()} says in advance
     * @since 1.1.0
     */
    default @Nullable DataDrivenSession showForm(DataDrivenForm form) {
        return null;
    }

    /**
     * Shows a data-driven message box on the connection.
     *
     * <p>How it ends, with which button if any, is reported to its result handler.
     *
     * @param messageBox the message box to show; built for one showing
     * @return a handle to the showing, or null when this connection doesn't support data-driven
     *         forms, which {@link #dataDrivenCapabilities()} says in advance
     * @throws IllegalStateException when the message box has already been shown
     * @since 1.1.0
     */
    default @Nullable DataDrivenSession showMessageBox(DataDrivenMessageBox messageBox) {
        return null;
    }

    /**
     * Transfer the connection to a server. A Bedrock player can successfully transfer to the same server they are
     * currently playing on.
     *
     * @param address the address of the server
     * @param port    the port of the server
     * @return true if the transfer was a success
     */
    boolean transfer(String address, @IntRange(from = 0, to = 65535) int port);
}
