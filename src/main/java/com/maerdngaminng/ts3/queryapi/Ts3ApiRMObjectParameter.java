package com.maerdngaminng.ts3.queryapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ts3ApiRMObjectParameter {

	public String value();
	public boolean editable() default false;
}
