package yang.coach.cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import yang.coach.utils.FileUtil;


/**
 * CPU相关工具类。
 */
class CpuUtils {

    static String[] getProcessCpuAction(int pid) {
        String cpuPath = "/proc/" + pid + "/stat";
        String cpu = "";
        String[] result = new String[3];

        File f = new File(cpuPath);
        if (!f.exists() || !f.canRead()) {
            /*
             * 进程信息可能无法读取，
			 * 同时发现此类进程的PSS信息也是无法获取的，用PS命令会发现此类进程的PPid是1，
			 * 即/init，而其他进程的PPid是zygote,
			 * 说明此类进程是直接new出来的，不是Android系统维护的
			 */
            return result;
        }

        FileReader fr = null;
        BufferedReader localBufferedReader = null;

        try {
            fr = new FileReader(f);
            localBufferedReader = new BufferedReader(fr, 8192);
            cpu = localBufferedReader.readLine();
            if (null != cpu) {
                String[] cpuSplit = cpu.split(" ");
                result[0] = cpuSplit[1];
                result[1] = cpuSplit[13];
                result[2] = cpuSplit[14];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtil.closeReader(localBufferedReader);
        return result;
    }

    static String[] getCpuAction() {
        String cpuPath = "/proc/stat";
        String cpu = "";
        String[] result = new String[7];

        File f = new File(cpuPath);
        if (!f.exists() || !f.canRead()) {
            return result;
        }

        FileReader fr = null;
        BufferedReader localBufferedReader = null;

        try {
            fr = new FileReader(f);
            localBufferedReader = new BufferedReader(fr, 8192);
            cpu = localBufferedReader.readLine();
            if (null != cpu) {
                result = cpu.split(" ");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtil.closeReader(localBufferedReader);
        return result;
    }
}