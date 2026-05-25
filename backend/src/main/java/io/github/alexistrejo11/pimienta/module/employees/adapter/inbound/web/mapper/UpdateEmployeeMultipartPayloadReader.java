package io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.mapper;

import io.github.alexistrejo11.pimienta.module.employees.adapter.inbound.web.dto.request.UpdateEmployeeRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

/**
 * Parses the multipart {@code employee} part as UTF-8 JSON (regardless of declared part
 * {@code Content-Type}) and validates it like a JSON body would be.
 */
@Component
public final class UpdateEmployeeMultipartPayloadReader {

  private static final Method READ_AND_VALIDATE;
  private static final MethodParameter EMPLOYEE_MULTIPART_PARAMETER;

  static {
    try {
      READ_AND_VALIDATE =
          UpdateEmployeeMultipartPayloadReader.class.getDeclaredMethod("readAndValidate", MultipartFile.class);
      EMPLOYEE_MULTIPART_PARAMETER = new MethodParameter(READ_AND_VALIDATE, 0);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  private final ObjectMapper objectMapper;
  private final Validator mvcValidator;

  public UpdateEmployeeMultipartPayloadReader(ObjectMapper objectMapper, Validator mvcValidator) {
    this.objectMapper = objectMapper;
    this.mvcValidator = mvcValidator;
  }

  /**
   * @throws ResponseStatusException 400 if the part is empty
   * @throws HttpMessageNotReadableException if JSON cannot be read
   * @throws MethodArgumentNotValidException if bean validation fails
   */
  public UpdateEmployeeRequest readAndValidate(MultipartFile employeePayload)
      throws MethodArgumentNotValidException {
    if (employeePayload.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "employee multipart part is empty");
    }
    UpdateEmployeeRequest parsed;
    try {
      parsed = objectMapper.readValue(employeePayload.getInputStream(), UpdateEmployeeRequest.class);
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("Malformed employee JSON part.", e, null);
    }

    if (parsed == null) {
      throw new HttpMessageNotReadableException(
          "Employee JSON part was empty or literal null.",
          new IllegalArgumentException("null employee JSON"),
          null);
    }

    BeanPropertyBindingResult validation =
        new BeanPropertyBindingResult(parsed, "employee", true, 100);
    mvcValidator.validate(parsed, validation);
    if (validation.hasErrors()) {
      throw new MethodArgumentNotValidException(EMPLOYEE_MULTIPART_PARAMETER, validation);
    }
    return parsed;
  }
}
