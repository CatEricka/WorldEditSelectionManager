package com.github.catericka.wsm.configuration;

/*
 * Copyright 2017 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.bukkit.command.CommandSender;

public enum Permissions
{
	ADMIN("wsm.admin");

	private final String permission;

	// Constructor
	Permissions(String permission)
	{
		this.permission = permission;
	}

	//
	public static boolean has(CommandSender sender, Permissions permission)
	{
		return has(sender, permission.permission);
	}

	//
	public static boolean has(CommandSender sender, String node)
	{
		return sender.hasPermission(node) || sender.hasPermission(node.toLowerCase());
	}

	//
	public String toString()
	{
		return permission;
	}
}
