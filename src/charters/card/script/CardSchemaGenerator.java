package charters.card.script;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper;

import charters.card.design.CharacterDesign;
import charters.card.design.ImprovementDesign;
import charters.card.design.ItemDesign;

public class CardSchemaGenerator
{
	public static void main(String[] args) 
	{
		generateCharterSchemas();
	}
	
	public static void generateCharterSchemas()
	{
		print("Generating Charter Schemas...");
		generateSchema(ImprovementDesign.class, new ImprovementDesign().getSchemaFilePath());
		generateSchema(CharacterDesign.class, new CharacterDesign().getSchemaFilePath());
		generateSchema(ItemDesign.class, new ItemDesign().getSchemaFilePath());
	}
	
	public static void generateSchema(Class<?> type, String outputFilePath)
	{
		print("Generating the Charter Schema for " + type.getSimpleName());
		HyperSchemaFactoryWrapper visitor = new HyperSchemaFactoryWrapper();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writerWithDefaultPrettyPrinter();
		try 
		{
			mapper.acceptJsonFormatVisitor(type, visitor);
		} 
		catch (JsonMappingException e) 
		{	
			e.printStackTrace();
		}
		JsonSchema improvementSchema = visitor.finalSchema();
		try 
		{
			String schemaString = mapper.writeValueAsString(improvementSchema);
			FileWriter outputFile = new FileWriter(new File(outputFilePath));
			outputFile.write(schemaString);
			print("...\n" + schemaString);
			outputFile.close();
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void print(String s)
	{
		System.out.println("[Card Schema Generator]: " + s);
	}
}
