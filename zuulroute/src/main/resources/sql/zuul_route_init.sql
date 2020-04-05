INSERT INTO `zuul_route`(id, path, service_id, url, strip_prefix, custom_sensitive_headers, retryable)
 VALUES ('route-user', '/route-user/**', 'user', null, true, false, false);