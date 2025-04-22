import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '任务标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '任务描述',
    align:"center",
    dataIndex: 'description'
   },
   {
    title: '任务分数',
    align:"center",
    dataIndex: 'points'
   },
   {
    title: '任务图标',
    align:"center",
    dataIndex: 'icon',
    customRender:render.renderImage,
   },
   {
    title: '任务学习材料',
    align:"center",
    dataIndex: 'learningMaterials',
   },
   {
    title: '是否启用',
    align:"center",
    dataIndex: 'status',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '是否为每日任务',
    align:"center",
    dataIndex: 'isDaily',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '任务标题',
    field: 'title',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务标题!'},
          ];
     },
  },
  {
    label: '任务描述',
    field: 'description',
    component: 'InputTextArea',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务描述!'},
          ];
     },
  },
  {
    label: '任务分数',
    field: 'points',
    component: 'InputNumber',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务分数!'},
          ];
     },
  },
  {
    label: '任务图标',
    field: 'icon',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
      },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务图标!'},
          ];
     },
  },
  {
    label: '任务学习材料',
    field: 'learningMaterials',
    component: 'JUpload',
    componentProps:{
     },
  },
  {
    label: '是否启用',
    field: 'status',
    defaultValue: "Y",
     component: 'JSwitch',
     componentProps:{
     },
  },
  {
    label: '是否为每日任务',
    field: 'isDaily',
    defaultValue: "Y",
     component: 'JSwitch',
     componentProps:{
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  title: {title: '任务标题',order: 0,view: 'text', type: 'string',},
  description: {title: '任务描述',order: 1,view: 'textarea', type: 'string',},
  points: {title: '任务分数',order: 2,view: 'number', type: 'number',},
  icon: {title: '任务图标',order: 3,view: 'image', type: 'string',},
  learningMaterials: {title: '任务学习材料',order: 4,view: 'file', type: 'string',},
  status: {title: '是否启用',order: 5,view: 'switch', type: 'string',},
  isDaily: {title: '是否为每日任务',order: 6,view: 'switch', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}