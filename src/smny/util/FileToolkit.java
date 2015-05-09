package smny.util;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class FileToolkit {
		public static File getConfigFile(String FileName){
    		String Path = null;
    		String name = null;
    		if(FileName.contains("/") || FileName.contains("\\")){
    				int index = FileName.lastIndexOf("/");
    				if(index==-1){
    						index = FileName.lastIndexOf("\\");
    				}
    				Path = FileName.substring(0,index);
    				name = FileName.substring(index+1);
    		}else{
    				name = FileName;
    		}
    		return getConfigFile(Path,name);
		}
		public static File getConfigFile(String Path,String name){
    		if(Path == null){
    				return getUserConfigFile(null,name);
    		}
    		return getUserConfigFile(Path.split("/|\\\\"),name);
		}
		public static File getUserConfigFile(String[] Path,String name){
    		int Level = 0;
    		File ConfigDir;
    		File UserDir = new File(System.getProperty("user.dir"));
    		if(Path==null || Path.length==0){
    				ConfigDir = UserDir;
    		}else{
    				if(StringToolkit.isNullOrEmpty(Path[0]) || ".".equals(Path[0].trim())){
    						Level++;
    						ConfigDir = UserDir;
    				}else if("..".equals(Path[0].trim())){
    						Level++;
    						ConfigDir = UserDir.getParentFile();
    				}
    		}
    		if(Path.length > Level){
    				ConfigDir = getConfigDir(UserDir,Path,Level);
    		}else{
    				ConfigDir = UserDir;
    		}
				UserDir = SearchFile(ConfigDir,name);
				if(UserDir == null){
						throw new NullPointerException("路径下:"+ConfigDir+"\r\n\t未找到文件“"+name+"”");
				}
    		return UserDir;
		}
		
		public static File getConfigDir(File UserDir,String[] PathLevel,int Level){
    		int i = Level;
    		File ConfigRootDir = null;
    		File IteratorFile = UserDir;
    		while(IteratorFile != null){
    				ConfigRootDir = SearchFile(IteratorFile,PathLevel[Level]);
    				if(ConfigRootDir != null){
    						break;
    				}
    				IteratorFile = IteratorFile.getParentFile();
    		}
				if(ConfigRootDir == null){
						throw new NullPointerException("当前路径:"+UserDir+"\r\n\t路径以下未找到指定路径根“"+PathLevel[Level]+"”");
				}
    		i++;
				File ConfigDir = ConfigRootDir;
    		while(i < PathLevel.length){
    				IteratorFile = SearchFile(ConfigDir,PathLevel[i]);
    				if(IteratorFile != null){
    						i++;
    						ConfigDir = IteratorFile;
    						continue;
    				}else if(ConfigRootDir.getParentFile().getParentFile() == null){
	    					throw new NullPointerException("当前路径:"+ConfigRootDir.getParentFile()+"\r\n\t已到根无法继续查找:"+PathLevel[Level]);
    				}else{
	    					return getConfigDir(ConfigRootDir.getParentFile().getParentFile(),PathLevel,Level);
    				}
    		}
				return ConfigDir;
		}
		
		public static File SearchFile(final File dir,final String FileName){
    		File[] MatchingFile = dir.listFiles(
    				new FilenameFilter(){
    						public boolean accept(File dir,String name){
    								return FileName.equalsIgnoreCase(name);
    						}
    				}
    		);
    		if(MatchingFile.length == 0){
    				return null;
    		}
    		return MatchingFile[0];
		}
		
		
		public static void main(String[] args)throws Exception{
    		System.out.println("配置文件:"+getConfigFile("Config/Test/GuideScript.js"));
		}
}
