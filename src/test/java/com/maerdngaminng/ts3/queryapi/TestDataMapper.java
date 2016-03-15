package com.maerdngaminng.ts3.queryapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.lang.reflect.InvocationTargetException;

import lombok.Getter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApiFactory;
import com.maerdngaminng.ts3.queryapi.rmo.InstanceInfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.ServerVersionInfo;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerState;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.BooleanDataMapper;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.DataMapper;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.DataMappingManager;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.IntDataMapper;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.StringDataMapper;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.VirtualServerStateDataMapper;

public class TestDataMapper {
	
	@BeforeClass
	public static void setup() {
		System.out.println("Setup server...");
	}

	@Getter
	private Ts3QueryApi api;

	public TestDataMapper() {
		this.init();
	}

	private void init() {
		this.api = Ts3QueryApiFactory.getDefaultFactory().createTs3QueryApi("localhost", 4231);
	}

	@Test
	public void testIntDataMapper() throws Ts3ApiException {
		DataMapper<Integer> intMapper = new IntDataMapper();
		int x = intMapper.mapData("234", getApi());
		assertEquals(x, 234);
		x = DataMappingManager.mapData(int.class, "234", getApi());
		assertEquals(x, 234);
	}
	
	@Test
	public void testBooleanDataMapper() throws Ts3ApiException {
		DataMapper<Boolean> booleanMapper = new BooleanDataMapper();
		boolean x = booleanMapper.mapData("1", getApi());
		assertEquals(x, true);
		x = booleanMapper.mapData("0", getApi());
		assertEquals(x, false);
		x = booleanMapper.mapData("true", getApi());
		assertEquals(x, true);
		x = booleanMapper.mapData("false", getApi());
		assertEquals(x, false);
	}

	@Test
	public void testStringDataMapper() throws Ts3ApiException {
		DataMapper<String> stringMapper = new StringDataMapper();
		String x = stringMapper.mapData("234\\s2333asefdr\\p", getApi());
		assertEquals(x, "234 2333asefdr|");
		x = DataMappingManager.mapData(String.class, "234\\s2333asefdr\\p", getApi());
		assertEquals(x, "234 2333asefdr|");
	}

	@Test
	public void testVirtualServerStateDataMapper() throws Ts3ApiException {
		DataMapper<VirtualServerState> virtualServerStateMapper = new VirtualServerStateDataMapper();
		VirtualServerState x = virtualServerStateMapper.mapData("online", getApi());
		assertEquals(x, VirtualServerState.ONLINE);
		x = virtualServerStateMapper.mapData("offline", getApi());
		assertEquals(x, VirtualServerState.OFFLINE);
		x = virtualServerStateMapper.mapData("onLiNe", getApi());
		assertEquals(x, VirtualServerState.ONLINE);
		x = virtualServerStateMapper.mapData("OFFLINE", getApi());
		assertEquals(x, VirtualServerState.OFFLINE);
		x = virtualServerStateMapper.mapData("werwer", getApi());
		assertNull(x);
		x = DataMappingManager.mapData(VirtualServerState.class, "online", getApi());
		assertEquals(x, VirtualServerState.ONLINE);
	}
	
	@Test
	public void testServerVersionMapping() throws InstantiationException, IllegalAccessException, Ts3ApiException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ServerVersionInfo info = DataMappingManager.mapToObject(ServerVersionInfo.class, "version=3.0.11.2 build=1418654632 platform=Linux", getApi());
		assertEquals(info.toString(), "ServerVersionInfo(version=3.0.11.2, build=1418654632, platform=Linux)");
	}

	@Test
	public void testVirtualServerMapping() throws InstantiationException, IllegalAccessException, Ts3ApiException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		VirtualServerSnapshot vsSnap = DataMappingManager.mapToObject(VirtualServerSnapshot.class,
				"virtualserver_id=1 virtualserver_port=9986 virtualserver_status=online virtualserver_clientsonline=3 virtualserver_queryclientsonline=0"
						+ " virtualserver_maxclients=18 virtualserver_uptime=11425036 virtualserver_name=Public\\sMaerdnGaming\\sTeamspeak virtualserver_autostart=1"
						+ " virtualserver_machine_id", getApi());
		assertEquals(vsSnap.toString(),
				"VirtualServerSnapshot(serverId=1, serverPort=9986, serverStatus=ONLINE, clientsOnline=3, queryClientsOnline=0, maxClients=18, uptime=11425036, "+
		"name=Public MaerdnGaming Teamspeak, autostart=true)");
		
		vsSnap = DataMappingManager.mapToObject(VirtualServerSnapshot.class,
				"virtualserver_id=321321 virtualserver_port=9986 virtualserver_status=offline virtualserver_clientsonline=3 virtualserver_queryclientsonline=0"
						+ " virtualserver_maxclients=18 virtualserver_uptime=11425036 virtualserver_name=Public\\sMaerdnGaming\\sTeamspeak virtualserver_autostart=0"
						+ " virtualserver_machine_id", getApi());
		assertEquals(vsSnap.toString(),
				"VirtualServerSnapshot(serverId=321321, serverPort=9986, serverStatus=OFFLINE, clientsOnline=3, queryClientsOnline=0, maxClients=18, uptime=11425036, "+
		"name=Public MaerdnGaming Teamspeak, autostart=false)");
	}
	
	@Test
	public void testInstanceInfoMapping() throws InstantiationException, IllegalAccessException, Ts3ApiException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		InstanceInfoSnapshot insSnap = DataMappingManager.mapToObject(InstanceInfoSnapshot.class,
				"serverinstance_database_version=23 serverinstance_filetransfer_port=30033 serverinstance_max_download_total_bandwidth=18446744073709551615 "+
				"serverinstance_max_upload_total_bandwidth=18446744073709551615 serverinstance_guest_serverquery_group=1 serverinstance_serverquery_flood_commands=50 "+
				"serverinstance_serverquery_flood_time=3 serverinstance_serverquery_ban_time=600 serverinstance_template_serveradmin_group=3 serverinstance_template_serverdefault_group=5 "+
				"serverinstance_template_channeladmin_group=1 serverinstance_template_channeldefault_group=4 serverinstance_permissions_version=19 "+
				"serverinstance_pending_connections_per_ip=0", getApi());
		assertEquals(insSnap.toString(), "InstanceInfoSnapshot(serverDatabaseVersion=23, filetransferPort=30033, maxDownloadBandwith=18446744073709551615, "+
				"maxUploadBandwith=18446744073709551615, guestServerQueryGroup=1, serverQueryFloodCommands=50, serverQueryFloodTime=3, serverQueryBanTime=600, templateServerAdminGroup=3, "+
				"templateServerDefaultGroup=5, templateChannelAdminGroup=1, templateChannelDefaultGroup=4, serverPermissionsVersion=19, serverPendungConnectionsPerIp=0)");
	}
	
	@Test
	public void testReverseMapping() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, Ts3ApiException {
		InstanceInfoSnapshot insSnap = DataMappingManager.mapToObject(InstanceInfoSnapshot.class,
				"serverinstance_database_version=23 serverinstance_filetransfer_port=30033 serverinstance_max_download_total_bandwidth=18446744073709551615 "+
				"serverinstance_max_upload_total_bandwidth=18446744073709551615 serverinstance_guest_serverquery_group=1 serverinstance_serverquery_flood_commands=50 "+
				"serverinstance_serverquery_flood_time=3 serverinstance_serverquery_ban_time=600 serverinstance_template_serveradmin_group=3 serverinstance_template_serverdefault_group=5 "+
				"serverinstance_template_channeladmin_group=1 serverinstance_template_channeldefault_group=4 serverinstance_permissions_version=19 "+
				"serverinstance_pending_connections_per_ip=0", getApi());
		insSnap.setGuestServerQueryGroup(255);
		assertNotSame(insSnap.toString(), "InstanceInfoSnapshot(serverDatabaseVersion=23, filetransferPort=30033, maxDownloadBandwith=18446744073709551615, "+
				"maxUploadBandwith=18446744073709551615, guestServerQueryGroup=1, serverQueryFloodCommands=50, serverQueryFloodTime=3, serverQueryBanTime=600, templateServerAdminGroup=3, "+
				"templateServerDefaultGroup=5, templateChannelAdminGroup=1, templateChannelDefaultGroup=4, serverPermissionsVersion=19, serverPendungConnectionsPerIp=0)");
		assertEquals(insSnap.toString(), "InstanceInfoSnapshot(serverDatabaseVersion=23, filetransferPort=30033, maxDownloadBandwith=18446744073709551615, "+
				"maxUploadBandwith=18446744073709551615, guestServerQueryGroup=255, serverQueryFloodCommands=50, serverQueryFloodTime=3, serverQueryBanTime=600, templateServerAdminGroup=3, "+
				"templateServerDefaultGroup=5, templateChannelAdminGroup=1, templateChannelDefaultGroup=4, serverPermissionsVersion=19, serverPendungConnectionsPerIp=0)");
		
		String line = ((Ts3QueryApiImpl) getApi()).buildCommandString(insSnap);
		System.out.println(line);
		
		assertEquals(line, "serverinstance_max_download_total_bandwidth=18446744073709551615 serverinstance_max_upload_total_bandwidth=18446744073709551615 "+
							"serverinstance_guest_serverquery_group=255");
	}
}
