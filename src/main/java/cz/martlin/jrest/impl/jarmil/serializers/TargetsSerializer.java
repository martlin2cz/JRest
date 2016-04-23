package cz.martlin.jrest.impl.jarmil.serializers;

import java.util.List;

import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.guest.NewObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.StaticClassOnGuestTarget;

public class TargetsSerializer {

	public TargetsSerializer() {
	}

	public TargetOnGuest deserialize(List<String> serialized) throws IllegalArgumentException {
		TargetType type = deserializeType(serialized.get(0));

		String identifier = serialized.get(1);
		switch (type) {
		case NEW:
			return NewObjectOnGuestTarget.create(identifier);
		case OBJECT:
			return ObjectOnGuestTarget.create(identifier);
		case STATIC:
			return StaticClassOnGuestTarget.create(identifier);
		default:
			throw new IllegalArgumentException("Unsupported target type " + type);
		}
	}

	private TargetType deserializeType(String string) {
		try {
			return TargetType.valueOf(string.toUpperCase());
		} catch (EnumConstantNotPresentException e) {
			throw new IllegalArgumentException("Target type " + string + " unknown");
		}
	}

	public void serialize(TargetOnGuest target, List<String> into) {
		String type = target.getType().name().toLowerCase();
		into.add(type);

		String identifier = target.getIdentifier();
		into.add(identifier);
	}
}
