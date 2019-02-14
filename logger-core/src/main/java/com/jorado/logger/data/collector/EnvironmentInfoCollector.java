package com.jorado.logger.data.collector;

import com.jorado.logger.data.EnvironmentInfo;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

/**
 * -- listing properties --
 * java.runtime.name=Java(TM) SE Runtime Environment
 * sun.boot.library.path=C:\Program Files\Java\jdk1.8.0_162\jr...
 * java.vm.version=25.162-b12
 * java.vm.vendor=Oracle Corporation
 * java.vendor.url=http://java.oracle.com/
 * path.separator=;
 * java.vm.name=Java HotSpot(TM) 64-Bit Server VM
 * file.encoding.pkg=sun.io
 * user.script=
 * user.country=CN
 * sun.java.launcher=SUN_STANDARD
 * sun.os.patch.level=Service Pack 1
 * java.vm.specification.name=Java Virtual Machine Specification
 * user.dir=D:\Work\_projects\campus-public
 * java.runtime.version=1.8.0_162-b12
 * java.awt.graphicsenv=sun.awt.Win32GraphicsEnvironment
 * java.endorsed.dirs=C:\Program Files\Java\jdk1.8.0_162\jr...
 * os.arch=amd64
 * java.io.tmpdir=C:\Users\LEN~1.ZHA\AppData\Local\Temp\
 * line.separator=
 * java.vm.specification.vendor=Oracle Corporation
 * user.variant=
 * os.name=Windows 7
 * sun.jnu.encoding=GBK
 * java.library.path=C:\Program Files\Java\jdk1.8.0_162\bi...
 * java.specification.name=Java Platform API Specification
 * java.class.version=52.0
 * sun.management.compiler=HotSpot 64-Bit Tiered Compilers
 * os.version=6.1
 * user.home=C:\Users\len.zhang
 * user.timezone=Asia/Shanghai
 * java.awt.printerjob=sun.awt.windows.WPrinterJob
 * file.encoding=UTF-8
 * java.specification.version=1.8
 * user.name=len.zhang
 * java.class.path=C:\Program Files\Java\jdk1.8.0_162\jr...
 * java.vm.specification.version=1.8
 * sun.arch.data.model=64
 * java.home=C:\Program Files\Java\jdk1.8.0_162\jre
 * sun.java.command=EventClient
 * java.specification.vendor=Oracle Corporation
 * user.language=zh
 * awt.toolkit=sun.awt.windows.WToolkit
 * java.vm.info=mixed mode
 * java.version=1.8.0_162
 * java.ext.dirs=C:\Program Files\Java\jdk1.8.0_162\jr...
 * sun.boot.class.path=C:\Program Files\Java\jdk1.8.0_162\jr...
 * java.vendor=Oracle Corporation
 * file.separator=\
 * java.vendor.url.bug=http://bugreport.sun.com/bugreport/
 * sun.cpu.endian=little
 * sun.io.unicode.encoding=UnicodeLittle
 * sun.desktop=windows
 * sun.cpu.isalist=amd64
 */

/**
 * LOCALAPPDATA=C:\Users\len.zhang\AppData\Local
 * PROCESSOR_LEVEL=6
 * FP_NO_HOST_CHECK=NO
 * USERDOMAIN=ZHAOPIN
 * LOGONSERVER=\\BJDC2
 * JAVA_HOME=C:\Program Files\Java\jdk1.8.0_162
 * FSHARPINSTALLDIR=C:\Program Files (x86)\Microsoft SDKs\F#\10.1\Framework\v4.0\
 * SESSIONNAME=Console
 * ALLUSERSPROFILE=C:\ProgramData
 * PROCESSOR_ARCHITECTURE=AMD64
 * GRADLE_HOME=D:\Work\gradle-4.7\
 * PSModulePath=C:\Windows\system32\WindowsPowerShell\v1.0\Modules\
 * SystemDrive=C:
 * ANT_HOME=D:\Work\apache-ant\
 * MAVEN_HOME=D:\Work\apache-maven\
 * APPDATA=C:\Users\len.zhang\AppData\Roaming
 * USERNAME=len.zhang
 * USERDNSDOMAIN=ZHAOPIN.COM.CN
 * windows_tracing_logfile=C:\BVTBin\Tests\installpackage\csilogfile.log
 * ProgramFiles(x86)=C:\Program Files (x86)
 * CommonProgramFiles=C:\Program Files\Common Files
 * Path=C:\ProgramData\Oracle\Java\javapath;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;d:\Program Files\Git\cmd;D:\Program Files\TortoiseGit\bin;D:\Program Files\nodejs\;C:\Program Files\dotnet\;C:\Program Files\Microsoft SQL Server\130\Tools\Binn\;D:\Work\gradle-4.7\\bin\;%ANT_HOME%\bin\;D:\Program Files\TortoiseSVN\bin;C:\Users\len.zhang\AppData\Local\Programs\Python\Python36\Scripts\;C:\Users\len.zhang\AppData\Local\Programs\Python\Python36\;C:\Users\len.zhang\AppData\Roaming\npm;D:\Program Files\Fiddler;C:\Program Files\Java\jdk1.8.0_162\bin;C:\Program Files\Java\jdk1.8.0_162\jre\bin;C:\Program Files\Microsoft VS Code\bin;D:\Work\apache-ant\\bin\;D:\Work\apache-ant\\lib\;D:\Work\apache-maven\\bin\;C:\Users\len.zhang\AppData\Local\GitHubDesktop\bin
 * PATHEXT=.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC
 * OS=Windows_NT
 * windows_tracing_flags=3
 * COMPUTERNAME=BJPD0070810
 * PROCESSOR_REVISION=5e03
 * CommonProgramW6432=C:\Program Files\Common Files
 * ComSpec=C:\Windows\system32\cmd.exe
 * UATDATA=C:\Windows\CCM\UATData\D9F8C395-CAB8-491d-B8AC-179A1FE1BE77
 * ProgramData=C:\ProgramData
 * ProgramW6432=C:\Program Files
 * HOMEPATH=\Users\len.zhang
 * SystemRoot=C:\Windows
 * TEMP=C:\Users\LEN~1.ZHA\AppData\Local\Temp
 * HOMEDRIVE=C:
 * PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 94 Stepping 3, GenuineIntel
 * USERPROFILE=C:\Users\len.zhang
 * TMP=C:\Users\LEN~1.ZHA\AppData\Local\Temp
 * CommonProgramFiles(x86)=C:\Program Files (x86)\Common Files
 * ProgramFiles=C:\Program Files
 * PUBLIC=C:\Users\Public
 * NUMBER_OF_PROCESSORS=4
 * windir=C:\Windows
 */
public class EnvironmentInfoCollector implements Collector {

    private static final long SIZE = 1024l * 1024l;

    @Override
    public EnvironmentInfo getData() {

        Properties p = System.getProperties();
        Map<String, String> env = System.getenv();
        EnvironmentInfo data = new EnvironmentInfo();
        if (env.containsKey("NUMBER_OF_PROCESSORS"))
            data.setProcessorCount(Integer.valueOf(env.get("NUMBER_OF_PROCESSORS")));
        data.setProcessName(getProcessName());
        data.setMemory(getMemory());
        data.setThreadName(Thread.currentThread().getName());
        data.setThreadId(Thread.currentThread().getId());
        data.setArchitecture(p.getOrDefault("os.arch", "").toString());
        data.setIpAddress(getIp());
        if (env.containsKey("COMPUTERNAME"))
            data.setMachineName(env.get("COMPUTERNAME"));
        if (env.containsKey("USERNAME"))
            data.setUserName(env.get("USERNAME"));
        data.setOsName(p.getOrDefault("os.name", "").toString());
        data.setOsVersion(p.getOrDefault("os.version", "").toString());
        data.setRuntimeVersion(p.getOrDefault("java.runtime.version", "").toString());
        return data;
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            return "127.0.0.1";
        }
    }

    private String getProcessName() {
        try {
            return ManagementFactory.getRuntimeMXBean().getName();
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * https://blog.csdn.net/wgw335363240/article/details/8878644
     *
     * @return
     */
    private String getMemory() {
        try {
            Runtime r = Runtime.getRuntime();
            return String.format("Xmx:%s M,Xms:%s M,-Xms %s M", r.maxMemory() / SIZE, r.totalMemory() / SIZE, r.freeMemory() / SIZE);
        } catch (Exception ex) {
            return "";
        }
    }

    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }
    }
}
