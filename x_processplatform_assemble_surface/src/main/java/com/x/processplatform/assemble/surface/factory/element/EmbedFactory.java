package com.x.processplatform.assemble.surface.factory.element;

import java.util.List;

import com.x.base.core.exception.ExceptionWhen;
import com.x.processplatform.assemble.surface.Business;
import com.x.processplatform.core.entity.element.Embed;
import com.x.processplatform.core.entity.element.Process;

public class EmbedFactory extends ElementFactory {

	public EmbedFactory(Business abstractBusiness) throws Exception {
		super(abstractBusiness);
	}

	public Embed pick(String flag) throws Exception {
		return this.pick(flag, ExceptionWhen.none);
	}

	public Embed pick(String flag, ExceptionWhen exceptionWhen) throws Exception {
		return this.pick(flag, Embed.class, exceptionWhen, Embed.FLAGS);
	}

	public List<Embed> listWithProcess(Process process) throws Exception {
		List<Embed> list = this.listWithProcess(Embed.class, process);
		return list;
	}
}