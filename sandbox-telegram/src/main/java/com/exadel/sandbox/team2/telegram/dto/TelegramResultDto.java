package com.exadel.sandbox.team2.telegram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramResultDto {
   String url;
   @JsonProperty("has_custom_certificate")
   boolean hasCustomCertificate;
   @JsonProperty("pending_update_count")
   int pendingUpdateCount;
}
