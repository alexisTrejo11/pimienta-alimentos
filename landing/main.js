/**
 * Pimienta Alimentos — landing interactions
 * Replace CONTACT_ENDPOINT with your real API when ready.
 */
(function () {
  "use strict";

  /**
   * Replace with your real API (POST JSON body: nombre, empresa|null, correo, mensaje).
   * For local testing over HTTP, https://httpbin.org/post echoes JSON and returns 200.
   */
  const CONTACT_ENDPOINT = "https://httpbin.org/post";

  const NAV_SELECTOR = ".site-nav";
  const NAV_LINK_SELECTOR = ".site-nav__link[data-nav]";
  const SECTION_SELECTOR = "main section[id]";

  function getNavHeight() {
    const nav = document.querySelector(NAV_SELECTOR);
    return nav ? nav.offsetHeight : 0;
  }

  function trimOrEmpty(value) {
    return typeof value === "string" ? value.trim() : "";
  }

  function isValidEmail(value) {
    if (!value) return false;
    // Practical HTML5-style check (not full RFC)
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  }

  /* ---------- Scroll spy (active nav in brand red) ---------- */

  function getSectionIdsInOrder() {
    return Array.from(document.querySelectorAll(SECTION_SELECTOR))
      .map(function (el) {
        return el.id;
      })
      .filter(Boolean);
  }

  function setActiveNav(id) {
    document.querySelectorAll(NAV_LINK_SELECTOR).forEach(function (link) {
      var target = link.getAttribute("data-nav");
      if (target === id) {
        link.classList.add("site-nav__link--active");
        link.setAttribute("aria-current", "page");
      } else {
        link.classList.remove("site-nav__link--active");
        link.removeAttribute("aria-current");
      }
    });
  }

  function computeActiveSectionId() {
    var ids = getSectionIdsInOrder();
    if (!ids.length) return "";

    var navH = getNavHeight();
    var scrollY = window.scrollY;
    var maxScroll = Math.max(0, document.documentElement.scrollHeight - window.innerHeight);

    if (scrollY + window.innerHeight >= maxScroll - 2) {
      return ids[ids.length - 1];
    }

    var marker = scrollY + navH + 2;
    var active = ids[0];

    ids.forEach(function (id) {
      var el = document.getElementById(id);
      if (!el) return;
      var top = el.offsetTop;
      if (top <= marker) active = id;
    });

    return active;
  }

  var scrollTicking = false;
  function onScrollOrResize() {
    if (scrollTicking) return;
    scrollTicking = true;
    requestAnimationFrame(function () {
      scrollTicking = false;
      var id = computeActiveSectionId();
      if (id) setActiveNav(id);
    });
  }

  function initScrollSpy() {
    window.addEventListener("scroll", onScrollOrResize, { passive: true });
    window.addEventListener("resize", onScrollOrResize);
    onScrollOrResize();
  }

  /* ---------- Contact form ---------- */

  function setFieldError(field, message) {
    var wrap = field.closest(".form__field");
    if (!wrap) return;
    var isTextarea = field.tagName === "TEXTAREA";
    field.classList.toggle(isTextarea ? "form__textarea--error" : "form__input--error", Boolean(message));
    field.setAttribute("aria-invalid", message ? "true" : "false");
    var hint = wrap.querySelector(".form__hint--error");
    if (hint) {
      hint.textContent = message || "";
      hint.hidden = !message;
    }
  }

  function clearFormErrors(form) {
    form.querySelectorAll(".form__input--error").forEach(function (el) {
      el.classList.remove("form__input--error");
      el.setAttribute("aria-invalid", "false");
    });
    form.querySelectorAll(".form__textarea--error").forEach(function (el) {
      el.classList.remove("form__textarea--error");
      el.setAttribute("aria-invalid", "false");
    });
    form.querySelectorAll(".form__hint--error").forEach(function (hint) {
      hint.textContent = "";
      hint.hidden = true;
    });
  }

  function validateContactForm(data) {
    var errors = {};
    if (!data.nombre) errors.nombre = "Ingrese su nombre.";
    if (!data.correo) errors.correo = "Ingrese su correo.";
    else if (!isValidEmail(data.correo)) errors.correo = "Correo no válido.";
    if (!data.mensaje) errors.mensaje = "Escriba un mensaje.";
    return errors;
  }

  function setFormStatus(el, type, message) {
    if (!el) return;
    el.hidden = !message;
    el.textContent = message;
    el.classList.remove("form__status--success", "form__status--error");
    if (type === "success") el.classList.add("form__status--success");
    if (type === "error") el.classList.add("form__status--error");
  }

  function initContactForm() {
    var form = document.getElementById("contact-form");
    if (!form) return;

    var statusEl = document.getElementById("contact-form-status");
    var submitBtn = form.querySelector('[type="submit"]');

    form.setAttribute("novalidate", "novalidate");

    form.addEventListener("submit", function (e) {
      e.preventDefault();
      clearFormErrors(form);
      setFormStatus(statusEl, "", "");

      var nombre = trimOrEmpty(form.elements.namedItem("nombre") && form.elements.namedItem("nombre").value);
      var empresa = trimOrEmpty(form.elements.namedItem("empresa") && form.elements.namedItem("empresa").value);
      var correo = trimOrEmpty(form.elements.namedItem("correo") && form.elements.namedItem("correo").value);
      var mensaje = trimOrEmpty(form.elements.namedItem("mensaje") && form.elements.namedItem("mensaje").value);

      var payload = {
        nombre: nombre,
        empresa: empresa || null,
        correo: correo,
        mensaje: mensaje,
      };

      var fieldErrors = validateContactForm({
        nombre: nombre,
        correo: correo,
        mensaje: mensaje,
      });

      var nombreInput = form.querySelector("#nombre");
      var correoInput = form.querySelector("#correo");
      var mensajeInput = form.querySelector("#mensaje");

      if (fieldErrors.nombre) setFieldError(nombreInput, fieldErrors.nombre);
      if (fieldErrors.correo) setFieldError(correoInput, fieldErrors.correo);
      if (fieldErrors.mensaje) setFieldError(mensajeInput, fieldErrors.mensaje);

      if (Object.keys(fieldErrors).length) {
        var firstKey = Object.keys(fieldErrors)[0];
        var firstEl = form.querySelector("#" + firstKey);
        if (firstEl) firstEl.focus();
        return;
      }

      if (submitBtn) {
        submitBtn.disabled = true;
        submitBtn.dataset.label = submitBtn.textContent;
        submitBtn.textContent = "Enviando…";
      }

      fetch(CONTACT_ENDPOINT, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(payload),
      })
        .then(function (res) {
          if (!res.ok) throw new Error("Respuesta no exitosa: " + res.status);
          return res.json().catch(function () {
            return {};
          });
        })
        .then(function () {
          setFormStatus(
            statusEl,
            "success",
            "Gracias. Hemos recibido su mensaje; nos pondremos en contacto pronto."
          );
          form.reset();
        })
        .catch(function () {
          setFormStatus(
            statusEl,
            "error",
            "No se pudo enviar el mensaje. Compruebe su conexión o configure CONTACT_ENDPOINT en main.js."
          );
        })
        .finally(function () {
          if (submitBtn) {
            submitBtn.disabled = false;
            submitBtn.textContent = submitBtn.dataset.label || "Enviar Solicitud";
          }
        });
    });

    ["nombre", "correo", "mensaje"].forEach(function (name) {
      var el = form.querySelector('[name="' + name + '"]');
      if (!el) return;
      el.addEventListener("input", function () {
        setFieldError(el, "");
        if (statusEl && !statusEl.hidden) {
          statusEl.hidden = true;
          statusEl.textContent = "";
        }
      });
    });
  }

  initScrollSpy();
  initContactForm();
})();
