package com.x.organization.assemble.control.alpha.jaxrs.departmentattribute;

import com.x.base.core.bean.BeanCopyTools;
import com.x.base.core.bean.BeanCopyToolsBuilder;
import com.x.organization.assemble.control.alpha.wrapin.WrapInDepartmentAttribute;
import com.x.organization.assemble.control.alpha.wrapout.WrapOutDepartmentAttribute;
import com.x.organization.core.entity.DepartmentAttribute;

public class ActionBase {
	protected static BeanCopyTools<DepartmentAttribute, WrapOutDepartmentAttribute> outCopier = BeanCopyToolsBuilder
			.create(DepartmentAttribute.class, WrapOutDepartmentAttribute.class, null,
					WrapOutDepartmentAttribute.Excludes);

	protected static BeanCopyTools<WrapInDepartmentAttribute, DepartmentAttribute> inCopier = BeanCopyToolsBuilder
			.create(WrapInDepartmentAttribute.class, DepartmentAttribute.class, null,
					WrapInDepartmentAttribute.Excludes);
}
